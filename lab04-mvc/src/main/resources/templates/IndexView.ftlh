<#import "common.ftl" as c>

<@c.page>
    <div class="content-wrapper">
        <div class="task-lists">
            <#if taskLists?size == 0>
                No task lists
            <#else>
                <#list taskLists as taskList>
                    <div class="task-list-item">
                        <@c.listLink taskList></@c.listLink>
                    </div>
                </#list>
            </#if>
        </div>
        <div class="board-wrapper">
            <@c.board todoTasks=todoTasks inProgressTasks=inProgressTasks completedTasks=completedTasks></@c.board>
        </div>
    </div>
</@c.page>
