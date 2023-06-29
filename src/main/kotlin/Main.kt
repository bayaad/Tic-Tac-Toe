import kotlin.math.max

enum class Player{
    X, O

}
class Board {
   private val grid: Array<Array<Player?>> = Array(3) {Array(3) {null} }

    fun createBoard(): Array<Array<Player?>> {
        return  grid
    }

    fun display(){
        for(row in grid){
            for(cell in row){
                val curr = cell?.name ?: "_"
                print("$curr ")
            }
                println()
        }

    }

    private fun validate(row: Int,  col: Int): Boolean{
        return !(row < 0 || row > grid.size-1 || col < 0 || col > grid.size || grid[row][col]!=null)

    }

    private fun isWiningMove(row: Int, col: Int, player: Player?): Boolean{
        var cnt = 0
        var flag =  0
        for (i in grid.indices) {
            if (grid[i][col] != player) {
                break
            }
            cnt++
        }
        flag = max(flag, cnt)
        cnt = 0
        for (i in grid.indices){
            if (grid[row][i] != player) {
                break
            }
           cnt++
        }
        flag = max(flag, cnt)
        cnt = 0
        if (row + col == grid.size - 1) {
            for (i in grid.indices) {
                if (grid[i][grid.size - 1 - i] != player) {
                    break
                }
                cnt++
            }
        }
        flag = max(flag, cnt)
        cnt = 0

        if (row == col) {
            for (i in grid.indices) {
                if (grid[i][i] != player) {
                    break
                }
                cnt++
            }
        }
        flag = max(flag, cnt)
        return flag==3


    }

    fun makeMove (pos: Pair<Int?, Int?>, player:Player?): Boolean {
        val row = pos.first
        val col = pos.second
        if(row == null || col == null){
            throw InvalidPositionException("No Correct Position")
        }
        if (validate(row, col)){
            grid[row][col] = player
            return isWiningMove(row, col, player)
        }else{
            throw InvalidPositionException("invalid position")
        }

    }

    fun isBoardFull(): Boolean {
        return grid.all { row -> row.all { it != null } }
    }


}






fun startGame(){
    val board =  Board()
    var currentPlayer = Player.X
    board.createBoard()
    var pos: Pair<Int?,Int?>
    while(true){
        if (board.isBoardFull()){
            println("DRAW!!!!!")
            return
        }
        println("Board:")
        board.display()
        println("Player ${currentPlayer.name} put in a position :")
        val inp = readlnOrNull()?.split(" ")
        pos = Pair(inp?.get(0)?.toIntOrNull(), inp?.get(1)?.toIntOrNull())
        try {
            if(board.makeMove(pos, currentPlayer)){
                println("Congrats Player $currentPlayer Won the Game")
                return
            }
            currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
        }catch (i:InvalidPositionException){
            println(i.message)
            continue
        }



    }


}


fun main() {

    startGame()
}