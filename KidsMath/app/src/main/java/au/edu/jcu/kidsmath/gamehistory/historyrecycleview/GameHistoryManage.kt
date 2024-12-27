package au.edu.jcu.kidsmath.gamehistory.historyrecycleview

object GameHistoryManager {
    private val gameHistoryItems = mutableListOf<GameHistoryItem>()

    // Add a game history item
    fun addGameHistoryItem(item: GameHistoryItem) {
        gameHistoryItems.add(item)
    }

    // Get all game history items
    fun getGameHistoryItems(): List<GameHistoryItem> {
        return gameHistoryItems
    }
}
