<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Task Viewer</title>
        <link rel="stylesheet" type="text/css" href="/style.css">
    </head>
    <script>
        function deleteAction(url) {
            const XHR = new XMLHttpRequest();
            XHR.addEventListener('load', function() {
                location.href = '/'
            });
            XHR.addEventListener('error', function() {
                location.href = '/'
            });

            XHR.open('DELETE', url);
            XHR.send();
        }
    </script>
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
<#-- @ftlvariable name="task" type="com.github.cannor147.itmo.software.lab04.model.TaskList" -->
    <a href="/task-list/${taskList.id}" class="list-link list-link-${taskList.id % 6}">${taskList.name}</a>
</#macro>

<#macro taskView task>
<#-- @ftlvariable name="task" type="com.github.cannor147.itmo.software.lab04.dto.TaskDto" -->
    <a href="/task/${task.id}"><b>${task.name}</b></a>
    <#if task.taskList??>
        <@listLink taskList=task.taskList></@listLink>
    </#if>
    <p>${task.description}</p>
</#macro>

<#macro boardView board>
<#-- @ftlvariable name="board" type="com.github.cannor147.itmo.software.lab04.dto.BoardDto" -->
    <div class="board">
        <div class="to-do column">
            <h3 class="column-name">To Do</h3>
            <#if board.todoTasks?size == 0>
                No tasks
            <#else>
                <ul>
                    <#list board.todoTasks as task>
                        <li><@taskView task></@taskView></li>
                    </#list>
                </ul>
            </#if>
        </div>
        <div class="in-progress column">
            <h3 class="column-name">In Progress</h3>
            <#if board.inProgressTasks?size == 0>
                No tasks
            <#else>
                <ul>
                    <#list board.inProgressTasks as task>
                        <li><@taskView task></@taskView></li>
                    </#list>
                </ul>
            </#if>
        </div>
        <div class="completed column">
            <h3 class="column-name">Completed</h3>
            <#if board.completedTasks?size == 0>
                No tasks
            <#else>
                <ul>
                    <#list board.completedTasks as task>
                        <li><@taskView task></@taskView></li>
                    </#list>
                </ul>
            </#if>
        </div>
    </div>
</#macro>
