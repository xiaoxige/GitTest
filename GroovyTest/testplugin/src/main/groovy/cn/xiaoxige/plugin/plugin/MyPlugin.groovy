package cn.xiaoxige.plugin.plugin

import cn.xiaoxige.plugin.extension.Xiaoxige
import cn.xiaoxige.plugin.task.XiaoxigeTask
import cn.xiaoxige.plugin.task.ZhuxiaoanTask
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by zhuxiaoan on 2017/12/10.
 */

public class MyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println('---------------------')
        println('--------begin--------')
        println('---------------------')
        project.extensions.create("xiaoxige", Xiaoxige)
        project.tasks.create('xiaoxigetask', XiaoxigeTask)
        project.tasks.create('zhuxiaoantask', ZhuxiaoanTask)

        project.android.applicationVariants.all { variant ->
            variant.outputs.each { output ->
                def file = output.outputFile
                output.outputFile = new File(file.parent, 'xiaoxige.apk')
            }
        }

    }
}
