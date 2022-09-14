package rk.enkidu.noteapplication

import android.content.Context

internal object FileHelper {
    fun writeTofile(fileModel: FileModel, context: Context){
        context.openFileOutput(fileModel.fileName, Context.MODE_PRIVATE).use{
            it.write(fileModel.data?.toByteArray())
        }
    }

    fun readFromFile(context: Context, fileName: String): FileModel {
        val fileModel = FileModel()
        fileModel.fileName = fileName
        fileModel.data = context.openFileInput(fileName).bufferedReader().useLines {
            it.fold("") { some, text ->
                "$some \n $text"
            }
        }

        return fileModel
    }
}
