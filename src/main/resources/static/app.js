const API = "http://localhost:8080";

// Start düyməsi logo animasiyasından sonra görünsün
window.addEventListener("load", () => {
    setTimeout(() => {
        document.getElementById("startBtn").classList.remove("hidden");
    }, 1100);
});

function openStep(stepId) {
    const el = document.getElementById(stepId);
    el.classList.add("active");
    el.scrollIntoView({ behavior: "smooth", block: "center" });
}

function setResult(id, text, isErr = false) {
    const el = document.getElementById(id);
    el.textContent = text;
    el.classList.toggle("err", isErr);
}

function resetFlow() {
    ["step1","step2","step3","step4"].forEach(id => {
        document.getElementById(id).classList.remove("active");
    });

    setResult("userResult", "-");
    setResult("planResult", "-");
    setResult("subResult", "-");
    setResult("payResult", "-");

    document.getElementById("currentUserId").textContent = "-";
    document.getElementById("currentSubId").textContent = "-";

    document.getElementById("subUserId").value = "";
    document.getElementById("subPlanId").value = "";
    document.getElementById("paySubId").value = "";

    document.getElementById("planSelect").innerHTML = `<option value="">Plan seç...</option>`;
    window.scrollTo({ top: 0, behavior: "smooth" });
}

function startApp() {
    document.getElementById("intro").classList.add("hidden");
    document.getElementById("app").classList.remove("hidden");

    resetFlow();
    openStep("step1");
}

async function safeJson(res) {
    const txt = await res.text();
    try { return txt ? JSON.parse(txt) : {}; } catch { return { raw: txt }; }
}

/* ===== 1) USER ===== */
async function createUser() {
    const fullName = document.getElementById("fullNameInput").value.trim();
    const email = document.getElementById("emailInput").value.trim();

    if (!fullName || !email) {
        setResult("userResult", "❌ fullName və email boş ola bilməz", true);
        return;
    }

    try {
        const res = await fetch(API + "/users", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ fullName, email })
        });

        const data = await safeJson(res);
        if (!res.ok) {
            setResult("userResult", "❌ " + (data.error || data.message || JSON.stringify(data)), true);
            return;
        }

        setResult("userResult", `✅ User yaradıldı: ${data.fullName} (id=${data.id})`);
        document.getElementById("currentUserId").textContent = data.id;

        // next step auto-fill
        document.getElementById("subUserId").value = data.id;

        // Step2 aç + planları AUTO gətir
        openStep("step2");
        await loadPlans(true);

    } catch {
        setResult("userResult", "❌ Backend işləmir (Spring run?)", true);
    }
}

/* ===== 2) PLANS ===== */
async function loadPlans(auto = false) {
    try {
        const res = await fetch(API + "/plans");
        const plans = await safeJson(res);

        if (!res.ok) {
            setResult("planResult", "❌ " + (plans.error || plans.message || JSON.stringify(plans)), true);
            return;
        }

        const select = document.getElementById("planSelect");
        select.innerHTML = `<option value="">Plan seç...</option>`;

        plans.forEach(p => {
            // monthlyPrice vs MonthlyPrice (səndə böyük hərflə ola bilər)
            const price = (p.monthlyPrice ?? p.MonthlyPrice ?? "-");
            const opt = document.createElement("option");
            opt.value = p.id;
            opt.textContent = `${p.name} (id=${p.id}, price=${price})`;
            select.appendChild(opt);
        });

        setResult("planResult", `✅ ${plans.length} plan yükləndi`);

        // auto=true olsa belə, plan seçilmədən step3 açılmır (düzdür)
        if (auto) {
            // nothing else
        }

    } catch {
        setResult("planResult", "❌ /plans çağırışı olmadı", true);
    }
}

// Plan seç → PlanId inputa yaz → Step3 aç
document.addEventListener("change", (e) => {
    if (e.target && e.target.id === "planSelect") {
        const planId = e.target.value;
        document.getElementById("subPlanId").value = planId;

        if (planId) {
            openStep("step3");
        }
    }
});

/* ===== 3) SUBSCRIPTION ===== */
async function createSubscription() {
    const userId = Number(document.getElementById("subUserId").value);
    const planId = Number(document.getElementById("subPlanId").value);

    if (!userId || !planId) {
        setResult("subResult", "❌ userId və planId yazılmalıdır", true);
        return;
    }

    try {
        const res = await fetch(API + "/subscriptions", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userId, planId })
        });

        const data = await safeJson(res);
        if (!res.ok) {
            setResult("subResult", "❌ " + (data.error || data.message || JSON.stringify(data)), true);
            return;
        }

        const subId = data.id ?? data.subscriptionId ?? "-";
        document.getElementById("currentSubId").textContent = subId;
        setResult("subResult", `✅ Subscription yaradıldı (id=${subId})`);

        // payment auto-fill
        document.getElementById("paySubId").value = subId;

        // Step4 aç
        openStep("step4");

    } catch {
        setResult("subResult", "❌ /subscriptions POST olmadı", true);
    }
}

/* ===== 4) PAYMENT ===== */
async function pay() {
    const subscriptionId = Number(document.getElementById("paySubId").value);

    if (!subscriptionId) {
        setResult("payResult", "❌ subscriptionId yazılmalıdır", true);
        return;
    }

    try {
        const res = await fetch(API + "/payments", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ subscriptionId })
        });

        const data = await safeJson(res);
        if (!res.ok) {
            setResult("payResult", "❌ " + (data.error || data.message || JSON.stringify(data)), true);
            return;
        }

        setResult("payResult", `✅ Paid: paymentId=${data.paymentId} • amount=${data.amount} • status=${data.status}`);

    } catch {
        setResult("payResult", "❌ /payments POST olmadı", true);
    }
}