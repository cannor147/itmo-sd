<#-- @ftlvariable name="user" type="com.github.cannor147.itmo.sd.lab11.dto.UserDto" -->
<#-- @ftlvariable name="companies" type="com.github.cannor147.itmo.sd.lab11.dto.CompanyDto[]" -->

<h1>${user.login}</h1>
<#assign accounts_funds = 0>
<#assign companies_funds = 0>
<#list user.accounts as account>
        <#assign accounts_funds = accounts_funds + account.amount + account.hold>
</#list>
<#list companies as company>
    <#assign companies_funds = companies_funds + (company.myAmount + company.myHold) * company.stockPrice>
</#list>
<div>Total funds: <span style="font-weight: bold; color: green">${accounts_funds + companies_funds}$</span></div>

<#if error??>
<h2>Error:</h2>
<span>${error}</span>
</#if>

<#if user.accounts?has_content>
<h2>Accounts:</h2>
<div>Total funds: <span style="font-weight: bold; color: green">${accounts_funds}$</span></div>
<ol>
    <#list user.accounts as account>
    <li>
        <h3>${account.name} account: <span style="color: darkgreen">${account.amount + account.hold}$</span> <span style="color: gray">(hold: ${account.hold}$)</span></h3>
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
</#if>

<#if companies?? && companies?has_content>
<h2>Exchange:</h2>
<div>Total funds: <span style="font-weight: bold; color: green">${companies_funds}$</span></div>
<ol>
    <#list companies as company>
    <li>
        <h3>${company.name} (${company.code}): <span style="font-weight: bold">${company.myAmount}</span>=<span style="color: green">${company.myAmount * company.stockPrice}$</span> <span style="color: gray">(hold: <span style="font-weight: bold">${company.myHold}</span>=${company.myHold * company.stockPrice}$)</span></h3>
        <div>Official rate: <span style="font-weight: bold; color: green">${company.stockPrice}$</span></div>
        <div>Available for sale in the exchange: <span style="font-weight: bold">${company.stockAmount}</span></div>
        <form method="post" action="/banking/${user.login}/exchange/buy" style="display: flex; flex-direction: row;">
            <input type="hidden" name="code" value="${company.code}">
            <label for="buy-id-${company.code}" style="min-width: 80px;">Buy:</label>
            <select id="buy-id-${company.code}" name="id">
                <option value="" selected></option>
                <#list company.deals as deal>
                    <option value="${deal.id}">${deal.amount} stocks</option>
                </#list>
            </select>
            <input type="submit" style="margin-left: 8px; margin-right: 8px;">
            <#if 0<company.openBuyingDeals>
            <div>Already buying: <span style="font-weight: bold">${company.openBuyingDeals}</span>=<span style="color: green">${company.openBuyingDeals * company.stockPrice}$</span></div>
            </#if>
            <details style="display: flex; flex-direction: row;">
                <summary><label for="sell-account-${company.code}">Account</label></summary>
                <select id="sell-account-${company.code}" name="account">
                    <#list user.accounts as account>
                    <option value="${account.name}" <#if account.name == "default">selected</#if>>${account.name}</option>
                    </#list>
                </select>
            </details>
        </form>
        <form method="post" action="/banking/${user.login}/exchange/sell" style="display: flex; flex-direction: row;">
            <input type="hidden" name="code" value="${company.code}">
            <label for="sell-amount-${company.code}" style="min-width: 80px;">Sell:</label>
            <input type="number" id="sell-amount-${company.code}" name="amount" value="0">
            <input type="submit" style="margin-left: 8px; margin-right: 8px;">
            <#if 0<company.openSellingDeals>
            <div>Already selling: <span style="font-weight: bold">${company.openSellingDeals}</span>=<span style="color: green">${company.openSellingDeals * company.stockPrice}$</span></div>
            </#if>
            <details style="display: flex; flex-direction: row;">
                <summary><label for="sell-account-${company.code}">Account</label></summary>
                <select id="sell-account-${company.code}" name="account">
                    <#list user.accounts as account>
                    <option value="${account.name}" <#if account.name == "default">selected</#if>>${account.name}</option>
                    </#list>
                </select>
            </details>
        </form>
    </li>
    </#list>
</ol>
</#if>