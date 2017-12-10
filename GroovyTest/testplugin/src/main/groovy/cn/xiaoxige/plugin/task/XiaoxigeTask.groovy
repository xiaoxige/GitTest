package cn.xiaoxige.plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public class XiaoxigeTask extends DefaultTask {

    @TaskAction
    public void defaultTask() {
        println(project.xiaoxige.toString())
    }

}