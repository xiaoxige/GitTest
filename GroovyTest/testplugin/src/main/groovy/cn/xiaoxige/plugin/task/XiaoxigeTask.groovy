package cn.xiaoxige.plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public class XiaoxigeTask extends DefaultTask {

    def add = { def x, def y ->
        x + y
    }

    @TaskAction
    public void defaultTask() {
        println(project.xiaoxige.toString())
        def sum = add 1, 2
        println(sum)

        def buildDir = project.buildDir
        println(buildDir)


    }

}