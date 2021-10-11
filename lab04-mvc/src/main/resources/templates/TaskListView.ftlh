<#import "common.ftl" as c>

<@c.page>
    <#if taskList??>
        <h2 class="title">${taskList.name}</h2>
        <@c.board todoTasks=todoTasks inProgressTasks=inProgressTasks completedTasks=completedTasks showListLink=false>
        </@c.board>
        <div class="date">Created: ${taskList.creationDate?date}</div>
    <#else>
        <div class="error">No such task list</div>
    </#if>
</@c.page>
