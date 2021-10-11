<#import "common.ftl" as c>

<@c.page>
    <#if task??>
        <div class="task">
            <b>${task.name}</b>
            <#if task.taskList??>
                <@c.listLink task.taskList></@c.listLink>
            </#if>
            <input onclick="deleteAction('/task-list/${task.id}')" type="image" src="/remove.png"
                   alt="Delete task list" class="icon-input delete-task">
            <p>${task.description}</p>
            <div class="date">Created: ${task.creationDate?date}</div>
        </div>
    <#else>
        <div class="error">No such task</div>
    </#if>
</@c.page>
