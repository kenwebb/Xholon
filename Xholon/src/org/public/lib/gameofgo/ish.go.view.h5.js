// Ish.Go namespace declaration
var Ish = Ish || {};
Ish.Go = Ish.Go || {};

// KSW running it within Xholon
// http://127.0.0.1:8080/war/Xholon.html?app=Game+of+Go&src=lstr&gui=clsc&jslib=jquery-3.5.1,gameofgo/ish.go,gameofgo/ish.go.logic,gameofgo/ish.go.view.h5&hide=xhgui,xhtabs,xhsvg,xhfooter
// http://127.0.0.1:8080/war/Xholon.html?app=Game+of+Go&src=lstr&gui=clsc&bsize=19x19&jslib=jquery-3.5.1,gameofgo/ish.go,gameofgo/ish.go.logic,gameofgo/ish.go.view.h5&hide=xhgui,xhtabs,xhsvg,xhfooter
// http://127.0.0.1:8080/war/Xholon.html?app=Game+of+Go&src=lstr&gui=clsc&bsize=9x9&jslib=jquery-3.5.1,gameofgo/ish.go,gameofgo/ish.go.logic,gameofgo/ish.go.view.h5&hide=xhgui,xhtabs,xhsvg,xhfooter

// begin Ish.Go.View namespace
Ish.Go.View = new function() {

	var canvas;
	var context;
	var isBoardMarked = false;
	
	const BSIZE_19x19 = "19x19";
	const BSIZE_9x9 = "9x9";
	const params = (new URL(document.location)).searchParams;
  const bsize = params.get('bsize'); // legal values are: "19x19" or "9x9"

	// KSW October 7, 2020
	const BOARD_19x19 = [19,19]; // width 19 x height 19
	const BOARD_9x9 = [9,9];
	const BOARD_DEFAULT = (bsize && (bsize == BSIZE_9x9)) ? BOARD_9x9 : BOARD_19x19;
	
	const PIXEL_19x19 = [522,522]; // pixel width x pixel height
	const PIXEL_9x9 = [252,252]; // 
	const PIXEL_DEFAULT = (bsize && (bsize == BSIZE_9x9)) ? PIXEL_9x9 : PIXEL_19x19;
	
	const BOARD_PNG_NAME_19x19  = "board.png";
	const BOARD_PNG_NAME_9x9  = "board_9x9.png";
	const BOARD_PNG_NAME_DEFAULT = (bsize && (bsize == BSIZE_9x9)) ? BOARD_PNG_NAME_9x9 : BOARD_PNG_NAME_19x19;
	
	// Static object for storing constants
	var ViewConstants = new function() {
		this.boardWidth = BOARD_DEFAULT[0]; //19; // KSW can't set it to 9 because board.png already has lines on it for 19
		this.boardHeight = BOARD_DEFAULT[1]; //19;
		this.boardPadding = 6;
		this.pieceWidth = 27;
		this.pieceHeight = 27;
		this.pixelWidth = PIXEL_DEFAULT[0]; // the actual pixel width of board.png
		this.pixelHeight = PIXEL_DEFAULT[1]; // the actual pixel height of board.png
		this.imgPieceBlack = "./xholon/lib/gameofgo/piece-black.png";
		this.imgPieceWhite = "./xholon/lib/gameofgo/piece-white.png";
		this.imgFlagBlack = "./xholon/lib/gameofgo/flag-black.png";
		this.imgFlagWhite = "./xholon/lib/gameofgo/flag-white.png";
	};

	// Object for tracking xy coords
	var Coords = function(x, y) {
		this.x = x;
		this.y = y;
	};

	// Tracks clicks on the board (canvas)
	var clickListener = function(e) {
		var point = Ish.Go.View.getPoint(e);
		if (point && !isBoardMarked) {
			Ish.Go.View.placePiece(point);
		}
	};

	// Tracks mouse movement over the board (canvas)
	var mouseMoveListener = function(e) {
		/*var coords = Ish.Go.View.getCanvasCoords(e);
		var point = Ish.Go.View.getPoint(e);
		
		$("#coords").html("(" + coords.x + ", " + coords.y + ")");
		
		if (point) {
			$("#point").html("(" + point.row + ", " + point.column + ")");
		} else {
			$("#point").html("(-, -)");
		}*/
	};
	
	/**
	 * Initializes a canvas and context for use in the View, but only if necessary
	 */
	var initCanvas = function() {
		if ($("#go-canvas").length == 0 || !canvas || !context) {
			canvas = document.createElement("canvas");
			canvas.id = "go-canvas";
			$("#board").append(canvas);
		
			canvas.width = ViewConstants.pixelWidth;
			canvas.height = ViewConstants.pixelHeight;
			canvas.style.background = "transparent url(./xholon/lib/gameofgo/" + BOARD_PNG_NAME_DEFAULT + ") no-repeat 0 0";
			
			canvas.addEventListener("click", clickListener, false);
			canvas.addEventListener("mousemove", mouseMoveListener);
			
			context = canvas.getContext("2d");
		}
	};

	// Given a mouse event, returns Coords relative to the canvas
	this.getCanvasCoords = function(e) {
		var x, y;
		
		// Get xy coords on page
		if (e.pageX != undefined && e.pageY != undefined) {
			x = e.pageX;
			y = e.pageY;
		} else {
			x = e.clientX + document.body.scrollLeft + document.documentElement.scrollLeft;
			y = e.clientY + document.body.scrollTop + document.documentElement.scrollTop;
		}
		
		// Narrow xy coords to canvas
		x -= canvas.offsetLeft;
		y -= canvas.offsetTop;
		
		return new Coords(x, y);
	};

	// Returns board Point from mouse event, or null if not a valid Point
	this.getPoint = function(e) {
		var coords = this.getCanvasCoords(e);
		var x = coords.x;
		var y = coords.y;
		
		// Remove padding from coords
		x -= ViewConstants.boardPadding;
		y -= ViewConstants.boardPadding;
		
		// Check if xy coords are in the padding
		if (x <= 0 || x >= ViewConstants.pixelWidth - (2 * ViewConstants.boardPadding) ||
				y <= 0 || y >= ViewConstants.pixelHeight - (2 * ViewConstants.boardPadding)) {
			return null;
		}
		
		// Get Point from xy coords on canvas
		var point = new Point(
			Math.floor(y/ViewConstants.pieceHeight),	// row
			Math.floor(x/ViewConstants.pieceWidth)	// column
		);
		return point;
	};

	// Given a Point, returns the top-left Coords on the canvas
	this.getCoordsFromPoint = function(point) {
		return new Coords(
			((point.column) * ViewConstants.pieceWidth) + ViewConstants.boardPadding,
			((point.row) * ViewConstants.pieceHeight) + ViewConstants.boardPadding
		);
	};

	// Places piece, and draws changes on the board
	this.placePiece = function(point) {
        var moveResult = Ish.Go.Logic.move(point.row, point.column);
        
        // Check for empty MoveResult (indicates invalid move)
        if (!moveResult) {
			var alertMsg = "Invalid Move";
			
			// Add specific message if present
			if (gGameState.moveError) {
				alertMsg += ":\n" + gGameState.moveError;
			}
			
			alert(alertMsg);
            return;
        }
		
		// Redraw board changes as a result of the move
		this.update(moveResult);
	};

	/**
	 * Draws piece on canvas
	 */
	this.drawPiece = function(point, color) {	
		var coords = this.getCoordsFromPoint(point);
		
		var piece = new Image();
		
		if (color == Constants.Color.BLACK) {
			piece.src = ViewConstants.imgPieceBlack;
		} else {
			piece.src = ViewConstants.imgPieceWhite;
		}
		
		piece.onload = function() {
			context.drawImage(piece, coords.x, coords.y);
		};
	};
	
	/**
	 * Draws territory on canvas
	 */
	this.drawTerritory = function(point, owner) {
		var coords = this.getCoordsFromPoint(point);
		
		var territory = new Image();
		
		if (owner == Constants.TerritoryOwner.BLACK) {
			territory.src = ViewConstants.imgFlagBlack;
		}
		else if (owner == Constants.TerritoryOwner.WHITE) {
			territory.src = ViewConstants.imgFlagWhite;
		}
		else { // Neutral
			return;
		}
		
		territory.onload = function() {
			context.drawImage(territory, coords.x, coords.y);
		};
	};
    
    this.removePieces = function(points) {
        var coords;
        $.each(points, function() { 
            coords = Ish.Go.View.getCoordsFromPoint(this);
            context.clearRect(
                coords.x,
                coords.y,
                ViewConstants.pieceWidth,
                ViewConstants.pieceHeight
            );
        });
    };
    
    this.update = function(moveResult) {
        if (moveResult) {
            // Draw only board changes
            this.drawPiece(moveResult.newPoint, moveResult.player.color);
            this.removePieces(moveResult.capturedPoints);
			
			this.drawInfo();
        }
    };
    
    this.redraw = function(canvasElement) {
        // Create canvas and context if necessary
        if (!canvasElement) {
			initCanvas();
        }
        
		this.drawBoard();
		this.drawInfo();
    };
	
	this.drawBoard = function() {
        context.clearRect(0, 0, ViewConstants.pixelWidth, ViewConstants.pixelHeight);        
        var point;
        var pState;
        for (var y = 0; y < gGameState.boardHeight; y++) {
            for (var x = 0; x < gGameState.boardWidth; x++) {
                point = new Point(y, x);
                pState = gGameState.getPointStateAt(point);
                if (pState == Constants.PointState.BLACK) {
                    this.drawPiece(point, Constants.Color.BLACK);
                }
                else if (pState == Constants.PointState.WHITE) {
                    this.drawPiece(point, Constants.Color.WHITE);
                }
            }
        }	
	};
	
	this.drawMarkedBoard = function() {
		var markedBoard = Ish.Go.Logic.getMarkedBoard();
		
        context.clearRect(0, 0, ViewConstants.pixelWidth, ViewConstants.pixelHeight);
        for (var y = 0; y < gGameState.boardHeight; y++) {
            for (var x = 0; x < gGameState.boardWidth; x++) {				
                this.drawTerritory(new Point(y,x), markedBoard[y][x]);
            }
        }
	};
	
	this.drawInfo = function() {
		// Print turn
		$("#turn").html("Current Turn: " + gGameState.currentPlayer.color);
		
		// Print scores		
		Ish.Go.Logic.setScores();
		
		var p1 = gGameState.player1;
		var p2 = gGameState.player2;
		
		$("#score").html("Score:" +
			"<br>&nbsp;&nbsp;" +
			p1.color + ": " + p1.score +
			"<br>&nbsp;&nbsp;" +
			p2.color + ": " + p2.score);		
	};
	
	/**
	 * Starts a new game.
	 */
	this.startNewGame = function() {
		Ish.Go.Logic.newGame(ViewConstants.boardWidth, ViewConstants.boardHeight); //(19, 19);
		
		this.redraw($("go-canvas"));
	};

	/**
	 * Prints code defining current game state on web page
	 */
	this.printGameState = function(aId) {
		var id = aId || 'gameState';
		var sBoard = "";

		// Initialize game state
		sBoard += "gGameState = new GameState(\n";
		sBoard += "\t" + gGameState.boardWidth + ",\n";
		sBoard += "\t" + gGameState.boardHeight + ",\n";
		sBoard += "\tnew Player(Constants.Color.BLACK, Constants.PointState.BLACK),\n";
		sBoard += "\tnew Player(Constants.Color.WHITE, Constants.PointState.WHITE)\n";
		sBoard += ");\n";
		
		// Set current player
		sBoard += "gGameState.currentPlayer = " +
			(gGameState.currentPlayer == gGameState.player1 ?
				"gGameState.player1;\n" :
				"gGameState.player2;\n");
		
		// Set board
		for (var y = 0; y < gGameState.boardHeight; y++) {
			sBoard += "gGameState.board[" + y + "] = [";
			for (var x = 0; x < gGameState.boardWidth; x++) {
				
				sBoard += "\"" + gGameState.board[y][x] + "\",";
			}
			sBoard = sBoard.substring(0, sBoard.length-1);
			sBoard += "];\n";
		}
		
		// Set previous board
		for (var y = 0; y < gGameState.boardHeight; y++) {
			sBoard += "gGameState.previousBoard[" + y + "] = [";
			for (var x = 0; x < gGameState.boardWidth; x++) {
				sBoard += "\"" + gGameState.previousBoard[y][x] + "\",";
			}
			sBoard = sBoard.substring(0, sBoard.length-1);
			sBoard += "];\n";
		}
		
		$("#" + id).html("<textarea>" + sBoard + "</textarea>");
	};
	
	/**
	 * Toggles between showing a regular or marked board.
	 * Merely calls appropriate print functions.
	 */
	this.toggleMarkedBoard = function() {
		isBoardMarked ?	this.drawBoard() : this.drawMarkedBoard();
		isBoardMarked = !isBoardMarked;
	};
	
	this.init = function() {
		this.xhinit();
		// Initialize game state
		gGameState = new GameState(
			ViewConstants.boardWidth, //19,
			ViewConstants.boardHeight, //19,
			new Player(Constants.Color.BLACK, Constants.PointState.BLACK),
			new Player(Constants.Color.WHITE, Constants.PointState.WHITE)
		);
		
		this.redraw();
	};
	
	this.xhinit = function() {
		// KSW Xholon July 21, 2020: create HTML elements with required class IDs
		var xhappspecific = document.querySelector("#xhappspecific");
		xhappspecific.innerHTML = `
		<div id="gameSection">
			<div id="board"></div>
			<div id="info">
				<div id="turn"></div>
				<div id="score"></div>
				<div id="coords"></div>
				<div id="point"></div>
			</div>
			<div class="clear"></div>
		</div>
		`;
	}
	
}; // end Ish.Go.View namespace
