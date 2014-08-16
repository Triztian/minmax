function Board() {
    this.marks=[];
	for(var i = 0; i < 3; i++)
		this.marks.push([undefined, undefined, undefined]);
}

Board.prototype.W= 3;
Board.prototype.X= 'X';
Board.prototype.O= 'O';

Board.prototype.setMark = function(cellNumber, mark) {
	for(var j = 0; j < this.W; j++)
		for(var i = 0; i < this.W; i++)
			if ( i + j * this.W == cellNumber)
				this.state[i][j]= mark;
}

/**
 * Obtain the values for a particular column.
 *
 * @param {Number} col The col number.
 * @return An array containing the marked cell numbers.
 *
 */
Board.prototype.getCols = function(col) {
    var cols = [];
	for(var n= 0; n < Board.W; n++)
        cols.push(this.marks[n][col]);

    return cols;
};


