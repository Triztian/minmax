/**
 * This class represents a Tic-Tac-Toe game
 *
 * @param {AI}      playerA The AI player.
 * @param {Player}  playerB The human player.
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
 * Here the turn is consumed.
 *
 * If it's the AI's turn the search and necessary calculates are performed
 * if it's the human player turn the mark is placed at the given cell number, if
 * the cell is not occupied.
 * 
 * @param {Number} cell The cell number where the mark will be placed.
 *
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
 * Obtain the winnig set triplets.
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
 * Render the board as it would be seen by the human player.
 * 
 * @param {Board} board The board to render.
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
 * Start the game.
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
