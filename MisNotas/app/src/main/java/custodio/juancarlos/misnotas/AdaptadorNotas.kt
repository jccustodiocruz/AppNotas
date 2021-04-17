package custodio.juancarlos.misnotas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import java.io.File
import java.lang.Exception

class AdaptadorNotas: BaseAdapter {
    var context: Context
    var notas = ArrayList<Nota>()

    constructor(context: Context, notas: ArrayList<Nota>){
        this.context = context
        this.notas = notas
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflador = LayoutInflater.from(context)
        var vista = inflador.inflate(R.layout.nota, null)
        var nota = notas[position]

        vista.titulo_nota.text = nota.titulo
        vista.contenido_nota.text = nota.contenido

        vista.btn_borrar.setOnClickListener{
            eliminar(nota.titulo)
            notas.remove(nota)
            this.notifyDataSetChanged()
        }

        return vista
    }

    override fun getItem(position: Int): Any {
        return notas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return notas.size
    }

    private fun eliminar(titulo: String){
        if(titulo == ""){
            Toast.makeText(context, "Titulo vacio", Toast.LENGTH_SHORT).show()
        }else{
            try {
                val archivo = File(ubicacion(), titulo+".txt")
                archivo.delete()

                Toast.makeText(context, "Se elimino correctamente", Toast.LENGTH_SHORT).show()
            }catch (e:Exception){
                Toast.makeText(context, "No se pudo eliminar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun ubicacion(): String{
        val album = File(context?.getExternalFilesDir(null), "notas")
        if(!album.exists()){
            album.mkdir()
        }
        return album.absolutePath
    }
}