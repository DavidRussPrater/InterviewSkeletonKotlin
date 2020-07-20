package `is`.digital.interviewskeleton

import `is`.digital.interviewskeleton.model.Todo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class TodoListViewAdapter(items: List<Todo>) : RecyclerView.Adapter<TodoListViewAdapter.ViewHolder>() {
    private var mValues: List<Todo>
    private val clickSubject: PublishSubject<Todo?> = PublishSubject.create()
    private val deleteSubject: PublishSubject<Todo?> = PublishSubject.create()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.todo_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mCheckbox.isChecked = mValues[position].completed
        holder.mContentView.setText(mValues[position].description)

        holder.mDeleteButton.setOnClickListener { v: View ->
            deleteSubject.onNext(holder.mItem!!)
        }

        holder.mView.setOnClickListener { v: View ->
            holder.mCheckbox.isChecked = !holder.mCheckbox.isChecked
            clickSubject.onNext(holder.mItem!!)
        }

        holder.mCheckbox.setOnClickListener { v: View ->
            holder.mCheckbox.isChecked = !holder.mCheckbox.isChecked
            clickSubject.onNext(holder.mItem!!)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    fun getItemClicks(): Observable<Todo?> {
        return clickSubject
    }

    fun getDeleteClicks(): Observable<Todo?> {
        return deleteSubject
    }


    class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mCheckbox: CheckBox
        val mContentView: TextView
        val mDeleteButton: Button
        var mItem: Todo? = null
        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }

        init {
            mCheckbox = mView.findViewById<View>(R.id.checkbox) as CheckBox
            mContentView = mView.findViewById<View>(R.id.content) as TextView
            mDeleteButton = mView.findViewById<Button>(R.id.delete_button) as Button
        }
    }

    init {
        mValues = items
    }
}