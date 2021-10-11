<#import "common.ftl" as c>

<@c.page>
    <#if taskList??>
        <h2 class="title">${taskList.name}</h2>
        <@c.boardView board=board></@c.boardView>
        <div class="date">Created: ${taskList.creationDate?date}</div>
    <#else>
        <div class="error">No such task list</div>
    </#if>
</@c.page>
