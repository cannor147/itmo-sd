<h1>${user.login}</h1>
<#if error??>
<h2>Error:</h2>
<span>${error}</span>
</#if>
<ol>
    <#list user.accounts as account>
    <li>
        <h3>${account.name} account: ${account.amount + account.hold}$ (hold: ${account.hold}$)</h3>
        <form method="post" action="/banking/${user.login}/deposit" style="display: flex; flex-direction: row;">
            <input type="hidden" name="account" value="${account.name}">
            <label for="deposit-amount-${account.name}" style="min-width: 80px;">Deposit:</label>
            <input type="number" id="deposit-amount-${account.name}" name="amount" value="0" min="0">
            <input type="submit" style="margin-left: 8px;">
        </form>
        <form method="post" action="/banking/${user.login}/withdraw" style="display: flex; flex-direction: row;">
            <input type="hidden" name="account" value="${account.name}">
            <label for="withdraw-amount-${account.name}" style="min-width: 80px;">Withdraw:</label>
            <input type="number" id="withdraw-amount-${account.name}" name="amount" value="0" min="0">
            <input type="submit" style="margin-left: 8px;">
        </form>
    </li>
    </#list>
</ol>