package custodio.juancarlos.misnotas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.*

class MainActivity : AppCompatActivity() {
    var notas = ArrayList<Nota>()
    lateinit var adaptador: AdaptadorNotas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ab_add.setOnClickListener{
            var intent = Intent(this, AgregarNotaActivity::class.java)
            startActivityForResult(intent, 123)
        }

        adaptador = AdaptadorNotas(this, notas)
        lv_notas.adapter = adaptador
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 123){
            leerNotas()
            adaptador.notifyDataSetChanged()
        }
    }

    fun leerNotas(){
        notas.clear()
        var carpeta = File(ubicacion().absolutePath)

        if (carpeta.exists()){
            var archivos = carpeta.listFiles()
            if (archivos != null){
                for(archivo in archivos){
                    leerArchivo(archivo)
                }
            }
        }
    }

    fun leerArchivo(archivo: File){
        val fis = FileInputStream(archivo)
        val di = DataInputStream(fis)
        val br = BufferedReader(InputStreamReader(di))
        var strLine: String? = br.readLine()
        var myData = ""

        while(strLine != null){
            myData = myData + strLine
            strLine = br.readLine()
        }

        br.close()
        di.close()
        fis.close()

        var nombre = archivo.name.substring(0, archivo.name.length-4)
        var nota = Nota(nombre.myData)
        notas.add(nota)
    }

    private fun ubicacion(): File{
        val folder = File(getExternalFilesDir(null), "")
        if(!folder.exists()){
            folder.mkdir()
        }
        return folder
    }
}

