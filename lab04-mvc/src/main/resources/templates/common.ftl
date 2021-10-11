<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Task Viewer</title>
        <link rel="stylesheet" type="text/css" href="/style.css">
    </head>
    <body>
    <header class="header">
<#--        <img src="https://img1.picmix.com/output/stamp/normal/2/9/8/3/1793892_62927.gif"-->
<#--             alt="Welcome to Task Viewer" title="Welcome to Task Viewer"/>-->
        <a href="/"><h1>Task Viewer</h1></a>
    </header>
    <main class="content">
        <#nested/>
    </main>
    <footer class="footer">
        <a href="#">Task Viewer</a> &copy; 2021 by Erofey Bashunov
    </footer>
    </body>
    </html>
</#macro>

<#macro listLink taskList>
    <a href="/task-list/${taskList.id}" class="list-link list-link-${taskList.id % 6}">${taskList.name}</a>
</#macro>

<#macro taskView task showListLink=true>
    <a href="/task/${task.id}"><b>${task.name}</b></a>
    <#if task.taskList?? && showListLink>
        <@listLink taskList=task.taskList></@listLink>
    </#if>
    <p>${task.description}</p>
</#macro>

<#macro board todoTasks inProgressTasks completedTasks showListLink=true>
    <div class="board">
        <div class="to-do column">
            <h3 class="column-name">To Do</h3>
            <#if todoTasks?size == 0>
                No tasks
            <#else>
                <ul>
                    <#list todoTasks as task>
                        <li><@taskView task showListLink=showListLink></@taskView></li>
                    </#list>
                </ul>
            </#if>
        </div>
        <div class="in-progress column">
            <h3 class="column-name">In Progress</h3>
            <#if inProgressTasks?size == 0>
                No tasks
            <#else>
                <ul>
                    <#list inProgressTasks as task>
                        <li><@taskView task showListLink=showListLink></@taskView></li>
                    </#list>
                </ul>
            </#if>
        </div>
        <div class="completed column">
            <h3 class="column-name">Completed</h3>
            <#if completedTasks?size == 0>
                No tasks
            <#else>
                <ul>
                    <#list completedTasks as task>
                        <li><@taskView task showListLink=showListLink></@taskView></li>
                    </#list>
                </ul>
            </#if>
        </div>
    </div>
</#macro>
