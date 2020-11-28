/*
 * Copyright 2020 Slawomir Czerwinski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package it.czerwinski.android.xpresso.recyclerview.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.czerwinski.android.xpresso.recyclerview.R

class TestRecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_recycler_view_activity)

        findViewById<RecyclerView>(R.id.textList).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TextAdapter()
        }

        findViewById<RecyclerView>(R.id.buttonsList).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ButtonAdapter()
        }
    }

    inner class TextAdapter : RecyclerView.Adapter<TextAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.text_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = textItems[position]
        }

        override fun getItemCount(): Int = textItems.size

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(R.id.text)
        }
    }

    inner class ButtonAdapter : RecyclerView.Adapter<ButtonAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.button_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.button.text = buttonItems[position]
            holder.button.setOnClickListener {
                this@TestRecyclerViewActivity
                    .findViewById<TextView>(R.id.selectedValue)
                    .text = buttonItems[position]
            }
        }

        override fun getItemCount(): Int = buttonItems.size

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val button: Button = itemView.findViewById(R.id.button)
        }
    }

    companion object {
        private val textItems = listOf("Red", "Green", "Blue")
        private val buttonItems = listOf("Cyan", "Magenta", "Yellow")
    }
}
