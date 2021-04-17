//package chess.web.domain.game;
//
//import chess.spring.controller.dto.request.MoveRequestDTO;
//import chess.spring.controller.dto.response.BoardStatusResponseDTO;
//import chess.spring.controller.dto.response.ChessGameResponseDTO;
//import chess.spring.controller.dto.response.GameStatusResponseDTO;
//import chess.web.dao.entity.ChessGameEntity;
//import chess.web.dao.entity.GameStatusEntity;
//import chess.web.dao.game.ChessGameRepository;
//import chess.web.domain.board.Board;
//import chess.web.domain.board.move.MoveRequest;
//import chess.web.domain.board.setting.BoardCustomSetting;
//import chess.web.domain.board.setting.BoardDefaultSetting;
//import chess.web.domain.board.setting.BoardSetting;
//import chess.web.domain.player.score.Scores;
//import chess.web.domain.player.type.TeamColor;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ChessGame {
//    private final Board board;
//    private final ChessGameRepository chessGameRepository;
//
//    public ChessGame(Board board, ChessGameRepository chessGameRepository) {
//        this.board = board;
//        this.chessGameRepository = chessGameRepository;
//    }
//
//    public Long createNew(BoardSetting boardSetting, String title) throws SQLException {
//        validate(boardSetting);
//        ChessGameEntity chessGameEntity = chessGameRepository.save(new ChessGameEntity(title));
//        board.createAndSaveNewPlayersAndPiecesPositionsOfGame(chessGameEntity.getId(), boardSetting);
//        return chessGameEntity.getId();
//    }
//
//    private void validate(BoardSetting boardSetting) {
//        if (!(boardSetting instanceof BoardDefaultSetting || boardSetting instanceof BoardCustomSetting)) {
//            throw new IllegalArgumentException("유효하지 않은 보드 세팅 객체 타입 입니다.");
//        }
//    }
//
//    public List<ChessGameResponseDTO> getAllGamesIdAndTitle() throws SQLException {
//        List<ChessGameResponseDTO> chessGameResponseDTOs = new ArrayList<>();
//        for (ChessGameEntity chessGameEntity : chessGameRepository.findAll()) {
//            chessGameResponseDTOs.add(new ChessGameResponseDTO(chessGameEntity.getId(), chessGameEntity.getTitle()));
//        }
//        return chessGameResponseDTOs;
//    }
//
//    public void move(MoveRequestDTO moveRequestDTO) throws SQLException {
//        ChessGameEntity chessGameEntity = chessGameRepository.findById(moveRequestDTO.getGameId());
////        MoveRequest moveRequest = new MoveRequest(chessGameEntity.getCurrentTurnTeamColor(), moveRequestDTO);
////        board.validateRoute(chessGameEntity.getId(), moveRequest);
////        board.move(chessGameEntity.getId(), moveRequest);
//    }
//
//    public BoardStatusResponseDTO getBoardStatus(Long gameId) throws SQLException {
//        return board.getStatus(gameId);
//    }
//
//    public GameStatusResponseDTO getGameStatus(Long gameId) throws SQLException {
//        GameStatusEntity gameStatusEntity = chessGameRepository.findStatusByGameId(gameId);
//        Scores scores = board.getScores(gameId);
//        return new GameStatusResponseDTO(
//            gameId,
//            gameStatusEntity.getTitle(),
//            gameStatusEntity.getCurrentTurnTeamColor(),
//            scores.getWhitePlayerScore(),
//            scores.getBlackPlayerScore());
//    }
//
//    public void changeToNextTurn(Long gameId) throws SQLException {
//        ChessGameEntity chessGameEntity = chessGameRepository.findById(gameId);
//        TeamColor currentTurnTeamColor = chessGameEntity.getCurrentTurnTeamColor();
//        chessGameEntity.setCurrentTurnTeamColor(currentTurnTeamColor.oppositeTeamColor());
//        chessGameRepository.updateCurrentTurnTeamColor(chessGameEntity);
//    }
//
//    public void remove(Long gameId) throws SQLException {
//        board.removeAllPlayersAndPiecesPositionsOfGame(gameId);
//        chessGameRepository.remove(gameId);
//    }
//}