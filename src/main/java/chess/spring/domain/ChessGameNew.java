package chess.spring.domain;

import chess.web.domain.board.move.MoveRequest;
import chess.web.domain.player.score.Scores;
import chess.web.domain.player.type.TeamColor;
import chess.web.domain.position.Position;

public class ChessGameNew {

    private static final String INITIAL_BOARD_STATUS = ""
        + "RNBQKBNR"
        + "PPPPPPPP"
        + "........"
        + "........"
        + "........"
        + "........"
        + "pppppppp"
        + "rnbqkbnr";
    private static final double INITIAL_SCORE = 38.0;
    private static final String INITIAL_TURN_TEAM_COLOR_VALUE = "white";

    private Long id;
    private final String title;
    private String boardStatus;
    private TeamColor currentTurnTeamColor;
    private double whitePlayerScore;
    private double blackPlayerScore;
    private final BoardNew board;

    public ChessGameNew(Long id, String title, String boardStatus, String currentTurnTeamColor, double whitePlayerScore, double blackPlayerScore) {
        this.id = id;
        this.title = title;
        this.boardStatus = boardStatus;
        this.currentTurnTeamColor = TeamColor.of(currentTurnTeamColor);
        this.whitePlayerScore = whitePlayerScore;
        this.blackPlayerScore = blackPlayerScore;
        this.board = new BoardNew(boardStatus);
    }

    public ChessGameNew(String title) {
        this(null, title, INITIAL_BOARD_STATUS, INITIAL_TURN_TEAM_COLOR_VALUE, INITIAL_SCORE, INITIAL_SCORE);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBoardStatus() {
        return boardStatus;
    }

    public TeamColor getCurrentTurnTeamColor() {
        return currentTurnTeamColor;
    }

    public double getWhitePlayerScore() {
        return whitePlayerScore;
    }

    public double getBlackPlayerScore() {
        return blackPlayerScore;
    }

    public boolean isKingDead() {
        return board.isKingDead();
    }

    public void movePiece(Position startPosition, Position destination) {
        MoveRequest moveRequest = new MoveRequest(currentTurnTeamColor, startPosition, destination);
        board.movePiece(moveRequest);
        updateScores();
        boardStatus = board.getBoardStatus();
        currentTurnTeamColor = currentTurnTeamColor.oppositeTeamColor();
    }

    private void updateScores() {
        Scores scores = board.getScores();
        whitePlayerScore = scores.getWhitePlayerScore();
        blackPlayerScore = scores.getBlackPlayerScore();
    }

    public String getCurrentTurnTeamColorValue() {
        return currentTurnTeamColor.getValue();
    }
}
