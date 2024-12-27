package au.edu.jcu.kidsmath.gamehistory.historyrecycleview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import au.edu.jcu.kidsmath.databinding.ItemGameHistoryBinding

class GameHistoryAdapter : RecyclerView.Adapter<GameHistoryAdapter.GameHistoryViewHolder>() {

    // List to hold the game history items
    private val gameHistoryItems = mutableListOf<GameHistoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHistoryViewHolder {
        // Inflate the item layout using data binding
        val binding =
            ItemGameHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameHistoryViewHolder, position: Int) {
        // Bind the data to the ViewHolder
        val item = gameHistoryItems[position]
        holder.bind(item)
    }

    override fun getItemCount() = gameHistoryItems.size

    // Update adapter data from GameHistoryManager
    @SuppressLint("NotifyDataSetChanged")
    fun updateItems() {
        // Clear the existing items
        gameHistoryItems.clear()
        // Add all items from the GameHistoryManager
        gameHistoryItems.addAll(GameHistoryManager.getGameHistoryItems())
        // Notify the adapter that the data set has changed
        notifyDataSetChanged()
    }

    class GameHistoryViewHolder(private val binding: ItemGameHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GameHistoryItem) {
            // Set the item for the ViewHolder and execute pending bindings
            binding.gameHistoryItem = item
            binding.executePendingBindings()
        }
    }
}
