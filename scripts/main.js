$(document).ready(function(){
    var ai = new AI('O', 'X');
    var game = new Game(ai, 'tic-tac-toe');
    

    $('#start').click(function() {
        game.play();
    });    


    $('#tic-tac-toe td').each(function(idx){
        $(this).click(function() {
            if (!game.started)
                return;

            var $this = $(this);

            // Player turn
            game.takeTurn(idx);

            // Ai turn
            game.takeTurn();
        });
    }); 
});
