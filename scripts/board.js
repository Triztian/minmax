/**
 * Representa un tablero de Tic-Tac-Toe.
 *
 * @param {String} markA El simbolo a utiliar para la marca del jugador A.
 * @param {String} markB El simbolo a utiliar para la marca del jugador B.
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
 * Colocar una marca dentro del tablero.
 * 
 * @param {Number}  cell El numero de celda en el cual se colocara la marca.
 * @param {Bool}    mark El tipo de marca que se va a utilizar.
 */
Board.prototype.setMark = function(cell, mark) {
    this.marks[cell]= this.markSymbol[mark];
    return this;
};

/**
 * Eliminar una marca dentro del tablero.
 * 
 * @param {Number}  cell El numero de celda en el cual se elminara la marca.
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
 * Obtener los indices de las celdas que pueden ser marcadas. Independiente 
 * del turno o jugador.
 *
 * @return Un arreglo que contiene los indices de las celdas que pueden ser 
 *         marcadas.
 */
Board.prototype.getMarkableCells= function() {
    var cells = [];
    for(var n=0; n<9; n++)
        if (this.marks[n] === undefined)
            cells.push(n);

    return cells;
};


/**
 * Contar el numero de marcas (turnos) que contiene el tablero.
 * Ya sea de ambos jugadores o de uno en particular.
 * Se considera marca si el valor dentro del arreglo no es
 * undefined
 *
 * @param {Bool} player 
 */
Board.prototype.countMarks= function(player) {
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
 * Obtener las marcas para una columna en especifico.
 *
 * @param {Number} col El indice de la column : 0 - 2
 * 
 * @return {Array} Un arreglo que contiene los valores de la 
 *                 columna especificada.
 */
Board.prototype.getCol = function(col) {
    var marks = [];
    for(var c= 0; c < 3; c++) 
        marks.push(this.marks[col + n * 3]);
    return marks;
};

/**
 * Obtener las marcas para una una de las dos diagonales.
 *  -Diagonal 0: [0, 4, 8]
 *  -Diagonal 1: [2, 4, 6]
 *
 * @param {Number} col El indice de la diagonal : 0 - 1
 * 
 * @return {Array} Un arreglo que contiene los valores de la 
 *                 diagonal especificada.
 */
Board.prototype.getDiagonal = function(diag) {
    var diags = [[0,4,8], [2,4,6]];
    var marks = [];
    for(var d in diags[diag])
        marks.push(this.marks[diags[diag][d]]);
    return marks;
};

/**
 * Obtener las marcas para una fila en especifico.
 *
 * @param {Number} col El indice de la fila : 0 - 2
 * 
 * @return {Array} Un arreglo que contiene los valores de la 
 *                 fila especificada.
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
