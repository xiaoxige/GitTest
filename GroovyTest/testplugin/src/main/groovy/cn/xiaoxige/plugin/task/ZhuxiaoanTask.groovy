package cn.xiaoxige.plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public class ZhuxiaoanTask extends DefaultTask {

    public ZhuxiaoanTask() {
        dependsOn 'xiaoxigetask'
    }

    @TaskAction
    public void zhuxiaoanAction() {
        println('zhuxiaoanAction')
    }

}