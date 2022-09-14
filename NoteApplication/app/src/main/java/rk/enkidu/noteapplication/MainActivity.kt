package rk.enkidu.noteapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import rk.enkidu.noteapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNew.setOnClickListener {
            newFile()
        }

        binding.buttonOpen.setOnClickListener {
            showList()
        }

        binding.buttonSave.setOnClickListener {
            saveFile()
        }
    }

    private fun saveFile() {
        when{
            binding.editTitle.text.toString().isEmpty() -> Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show()
            binding.editFile.text.toString().isEmpty() -> Toast.makeText(this, "Content cannot be empty", Toast.LENGTH_SHORT).show()
            else -> {
                val fileModel = FileModel()
                fileModel.fileName = binding.editTitle.text.toString()
                fileModel.data = binding.editFile.text.toString()
                FileHelper.writeTofile(fileModel, this)
                Toast.makeText(this, "Saving " + fileModel.fileName + " file", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showList(){
        val items = fileList()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose File : ")
        builder.setItems(items){dialog, item ->
            loadData(items[item].toString())
        }
        val alert = builder.create()
        alert.show()
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this, title)
        binding.editTitle.setText(fileModel.fileName)
        binding.editFile.setText(fileModel.data)
        Toast.makeText(this, "Loading " + fileModel.fileName + " data", Toast.LENGTH_SHORT).show()
    }

    private fun newFile() {
        binding.editTitle.setText("")
        binding.editFile.setText("")
        Toast.makeText(this, "Clearing File", Toast.LENGTH_SHORT).show()
    }
}