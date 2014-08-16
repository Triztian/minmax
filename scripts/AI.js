/**
 * Represents the player with the AI.
 *
 * @param {Number} lookahead Number of states (movements) that the AI explores
 *                           before making its move.
 */
function AI(myMark, opMark) {
    this.board= undefined;
    this.myMark= myMark;
    this.opMark= opMark;
}

/**
 * Constant to indicate that the player is the maximising player.
 */
AI.prototype.I_AM_MAX = true;

/**
 * This is the heuristic evaluation function. It determines the score of each 
 * player so that the AI can minimize the "damage" inflicted by the oponent
 * 
 * This function determines the posible marks that a player might have in 
 * rows, columns or diagonals that would allow him to win the game.
 * 
 * Score is assigned in the following way.
 *  - 1 mark = 1 pt
 *  - 2 marks = 10 pt
 *  - 3 marks = 100 pt
 * 
 * @param {Board} board Represents the game board.
 * @param {Bool}  isMax Indicates that the game is analyzed from the human or oponents perspective.
 * 
 * @return The boards score.
 */
AI.prototype.score = function score(board, isMax) {
    // Configuraciones
    var self = this;
    var F = function(e) { 
        return e === (isMax ? self.myMark : self.opMark); 
    };
    var sets = [
         [0,1,2], [3,4,5], [6,7,8], // Rows
         [0,3,6], [1,4,7], [2,5,8], // Cols
         [0,4,8], [2,4,6]           // Diags
    ];

	var score= 0;
    for(var s in sets) {
        var setm = board.getCells(sets[s]);
        var count= setm.filter(F).length;
        if (count > 0) {
            score += Math.pow(10, count - 1);
        }
    }
    score = isMax ? score : -score;
    if (Math.abs(score) >= 100)
        console.log('Winning move: ', board.toString(), score);
    return score;
};


/** 
 * The minmax algorithm.
 * 
 * @param {Number} depth    The depth of the tree. 
 *                          If it is the first call the number of possible states (future movements)
 *                          to consider must be included.
 * @param {Bool}   isMax    Indicates if this is the maximizing player if otherwise it is considered the minimizing player.
 * @param {Number} alpha    The alpha value that is used for trimming the tree.
 * @param {Number} beta     The beta value that is used for trimming the tree.
 * 
 * @return The calculated score.
 */
AI.prototype._minimax = function(depth, isMax, alpha, beta) {
    var bestCell = -1;
    var score = undefined;
    var nextMoves = this.getNextMoves();

    if (nextMoves.length === 0 || depth === 0)
       return [this.score(this.board, isMax), bestCell];

    for(var c in nextMoves) {
        var move= nextMoves[c];
		console.log('Next moves: ' + nextMoves);
		this.board.marks[move]= isMax? 'X' : 'O';
		if (isMax) {
            score = this._minimax(depth - 1, !isMax, alpha, beta)[0];
			console.log('Max(' + move + ') => ' + this.board.toString() + ' = ' + score);
            if (score > alpha) { 
                alpha = score;
                bestCell = move;
            }
        } else {
             score = this._minimax(depth - 1, isMax, alpha, beta)[0];
			 console.log('Min(' + move + ') => ' + this.board.toString() + ' = ' + score);
            if (score < beta) { 
                beta = score;
                bestCell = move;
            }
        }
       this.board.marks[move]= undefined;

        if (alpha >= beta) break; 
    }

    return [isMax ? alpha : beta, bestCell];
};


/**
 * Obtain the next possible moves.
 */
AI.prototype.getNextMoves = function() {
    for(var n= 0; n < 3; n++);
        
    return this.board.getMarkableCells();
}


/**
 * Make a movement within the board.
 */
AI.prototype.makeMove = function() {
    var result = undefined;
    var marks = this.board.countMarks();
    
    if (marks === 0) {
        result = [Infinity, parseInt(Math.random() * 9)];
    } else { 
        result = this._minimax(2, AI.I_AM_MAX, -Infinity, Infinity);
    }
    
    return this.board.setMark(result[1], true);
};
