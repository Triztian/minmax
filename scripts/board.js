/**
 * Represents the Tic-Tac-Toe board.
 *
 * @param {String} markA The symbol to use for the A player.
 * @param {String} markB The symbol to use for the B player.
 */
function Board(markA, markB) {
    this.markSymbol = {};
    this.markSymbol[true] = markA;
    this.markSymbol[false] = markB;

    this.marks= [];
	for(var i = 0; i < 9; i++)
		this.marks.push(undefined);
}

Board.prototype.X= 'X';
Board.prototype.O= 'O';

/**
 * Set a mark within the board.
 * 
 * @param {Number}  cell The cell number in which the mark will be placed.
 * @param {Bool}    mark The mark type that will be used.
 */
Board.prototype.setMark = function(cell, mark) {
    this.marks[cell]= this.markSymbol[mark];
    return this;
};

/**
 * Remove a mark from the board.
 * 
 * @param {Number}  cell The cell number which's mark will be removed.
 */
Board.prototype.clearMark = function(cell) {
    this.marks[cell]= undefined;
    return this;
};

Board.prototype.getCells = function(cells) {
    var self = this;
    return cells.map(function(i) { return self.marks[i];});
};


/**
 * Obtain the index of the cells that can be marked, regardless of the turn
 *
 * @return An array that contains the cell indexes that can be marked.
 */
Board.prototype.getMarkableCells= function() {
    var cells = [];
    for(var n=0; n<9; n++)
        if (this.marks[n] === undefined)
            cells.push(n);

    return cells;
};


/**
 * Count the number of marks in the board.
 *
 * @param {Bool} player 
 */
Board.prototype.countMarks = function(player) {
    var self = this;
    var F = function(e) {
        return e !== undefined; 
    }
    var M = function(e) {
        return self.markSymbol[player] === e;
    };
    return this.marks.filter(player === undefined ? F : M).length;
};


/**
 * Obtain the marks of a column.
 *
 * @param {Number} col The columns index 0 to 2
 * 
 * @return {Array} An array that containes the values of the marked cells.
 *
 */
Board.prototype.getCol = function(col) {
    var marks = [];
    for(var c= 0; c < 3; c++) 
        marks.push(this.marks[col + n * 3]);
    return marks;
};

/**
 *  Obtain the marks for one of the two diagonals.
 *  -Diagonal 0: [0, 4, 8]
 *  -Diagonal 1: [2, 4, 6]
 *
 * @return {Array} An array that contains the marked cells
 *
 */
Board.prototype.getDiagonal = function(diag) {
    var diags = [[0,4,8], [2,4,6]];
    var marks = [];
    for(var d in diags[diag])
        marks.push(this.marks[diags[diag][d]]);
    return marks;
};

/**
 * Obtain the marks for a particular row.
 *
 * @return {Array} An array containing the cell numbers that are marked.
 *
 */
Board.prototype.getRow = function(row) {
    var marks = [];
    for(var c= 0; c < 3; c++) 
        marks.push(this.marks[n + row * 3]);
    return marks;
};

Board.prototype.toString = function() {
    var m= this.marks.map(function(e) {
        return e !== undefined ? e : '_';
    });
    return m.join('');
};
