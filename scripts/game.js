/**
 * Esta clase representa una partida de tic tac toe.
 *
 * @param {AI}      playerA El jugador con IA.
 * @param {Player}  playerB El jugador humano.
 */
function Game(ai, viewPort) {
    this.ai = ai;
    this.viewPort = viewPort;

    this.started = false;
    this.turn = 1;
    this.winner= undefined;
    this.board = new Board('O', 'X');
}


/** 
 * Aqui se utiliza el turno, si es el turno del IA entonces 
 * se lleva a cabo la busqueda y calculos necesarios para optimizar 
 * la partica a favor de ella.
 * Si es el del jugador humano enconces solo se establece la marca
 * en el lugar indicado.
 * 
 * @param {Number} cell El indice de la celda donde se establecera la
 *                      marca.
 */
Game.prototype.takeTurn = function(cell) {
    if (this.winner)
        return;

    if (this.board.marks[cell] !== undefined) 
        return;
    
    if (this.turn % 2 === 0) {
        console.log(new Date() + this.turn + ': AI move');
        this.ai.board = this.board;
        this.board = this.ai.makeMove();
    } else {
        console.log(new Date() + this.turn + ': Player move');
        this.board = this.board.setMark(cell, false);
    }

    if (this.getWinningSet())
        this.winner = true;
    
    this.turn++;
    var maxscore = this.ai.score(this.board, true);
    var minscore = this.ai.score(this.board, false);

    console.log(new Date() + 'Rendering: ' + this.board.toString());
    console.log(new Date() + 'Scores AI, Player = ', maxscore, minscore);
    this._render(this.board);
};

/**
 * Obtener el set o la terna que otorga la victoria
 *
 */
Game.prototype.getWinningSet = function() {
    var sets = [
        [0,1,2], [3,4,5], [6,7,8],
        [0,3,6], [1,4,7], [2,5,8],
        [0,4,8], [2,4,6]
    ];
    for(var s in sets) {
        var setv = this.board.getCells(sets[s]);
        for(var c in 'OX') {
            var F= function(e) { return e === 'OX'[c]; };
            if (setv.filter(F).length === 3)
                return sets[s];
        }
    }
    return undefined;
};

/** 
 * Renderizar el tabler a como lo veria el jugador humano.
 * 
 * @param {Board} board El tablero a renderizar.
 */
Game.prototype._render = function(board) {
    var wset = this.getWinningSet();
    $('#' + this.viewPort + ' td').each(function(idx) {
        var m = board.marks[idx];
        var $this= $(this);

        $this.html(m === undefined ? '' : m);

        if (wset && wset.indexOf(idx) !==-1)
            $this.addClass('winning');
        else
            $this.removeClass('winning');
            
    });
    $('#turn').html('Turno: ' + this.turn + 
                    ' (' + (this.turn % 2 !== 0 ? 'Jugador':'AI') + ')');
};


/**
 * Funcion que inicia el juego.
 */
Game.prototype.play = function() {
    this.turn = 1;
    this.started= true;
    this.winner= undefined;
    this.board = new Board('O', 'X');
    this._render(this.board);
};

/** 
 * Funcion que determina si el juego ha concluido.
 */
Game.prototype.gameOver = function() {
    var sym = ['O', 'X'];

    for(var s in sym) {
        var F = function(e) { return e === sym[s]; };
        for(var i= 0; i < 3; i++) {
            var sets= [
                this.board.getCol(i), 
                this.board.getRow(i), 
            ];
            for(var f in sets)
                if (sets[f].filter(F).length === 3)
                    return true;
        }
        
        var diags = [0,1].map(this.board.getDiagonal);
        for(var d in diags)
            if (diags[d].filter(F).length === 3)
                return true;
    }

    return false;
};
