/**
 * Representa a el jugador con inteligencia artificial.
 *
 * @param {Number} lookahead El numero de movientos (estados) a futuro que 
 *                           considera antes de hacer su movimiento.
 */
function AI(myMark, opMark) {
    this.board= undefined;
    this.myMark= myMark;
    this.opMark= opMark;
}

/**
 * Constante para indicar que es el jugador
 * maximizante.
 */
AI.prototype.I_AM_MAX = true;

/**
 * Esta funcion es la funcion "Heuristica de Evaluacion". Se encarga
 * de determinar el puntaje de cada jugador de manera que pueda 
 * minimizar el maximo dano infligido por el oponente.
 * 
 * Esta funcion evalua cuanta marcas del jugador hay en las posibles
 * filas, columnas o diagonales que permiten ganar el juego.
 * 
 * El puntaje se asignara de la siguiente manera:
 *  - 1 marcas = 1 pt
 *  - 2 marcas = 10 pt
 *  - 3 marcas = 100 pt
 * 
 * @param {Board} board Representa al tablero de juegos
 * @param {Bool}  isMax Indica si el tablero a evaluar sera desde la
 *                      perspectiva de el jugador o el oponente.
 * 
 * @return El puntaje del tablero.
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
    return score;
};


/** 
 * El algoritmo minimax.
 * 
 * @param {Number} depth    La profundidad del arbol. 
 *                          Si es la llamada inicial incluir los el numero
 *                          de estados "virtuales" o movimientos a futuro
 *                          que desamos considerar.
 * @param {Bool}   isMax    Indica si el jugador es el maximizante 
 *                          o minimizante.
 * @param {Number} alpha    EL valor de alpha a utilizar para el podado.
 * @param {Number} beta     EL valor de beta a utilizar para el podado.
 * 
 * @return El puntaje calculado
 */
AI.prototype._minimax = function(depth, isMax, alpha, beta) {
    console.log(arguments);
    var bestCell= -1;
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
 * Funcion que genera una lista de las posibles siguentes celdas a ocupar.
 */
AI.prototype.getNextMoves = function() {
    return this.board.getMarkableCells();
}


/*
 * Realizar el movimiento dentro del tablero.
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
