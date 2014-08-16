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
 * Obtener los valores de una columna en especial.
 *
 * @param {Number} col La columna para la cual se obtendran los valores
 *                     Inician desde 0.
 * @return Un arreglo que contiene los valores de la columna especificada
 */
Board.prototype.getCols = function(col) {
    var cols = [];
	for(var n= 0; n < Board.W; n++)
        cols.push(this.marks[n][col]);

    return cols;
};


