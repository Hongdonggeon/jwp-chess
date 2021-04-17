package chess.spring.dao;


import chess.spring.domain.ChessGameNew;
import chess.spring.repository.ChessGameRepositoryNew;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ChessGameDaoNew implements ChessGameRepositoryNew {

    private final JdbcTemplate jdbcTemplate;

    public ChessGameDaoNew(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ChessGameNew> chessGameRowMapper = (resultSet, rowNum) ->
        new ChessGameNew(
            resultSet.getLong("chess_game_id"),
            resultSet.getString("title"),
            resultSet.getString("board_status"),
            resultSet.getString("current_turn_team_color"),
            resultSet.getDouble("white_player_score"),
            resultSet.getDouble("black_player_score"));

    @Override
    public Long save(ChessGameNew chessGame) {
        String query = "INSERT INTO chess_game "
            + "(title, board_status, current_turn_team_color, white_player_score, black_player_score) "
            + "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"chess_game_id"});
            ps.setString(1, chessGame.getTitle());
            ps.setString(2, chessGame.getBoardStatus());
            ps.setString(3, chessGame.getCurrentTurnTeamColor().getValue());
            ps.setDouble(4, chessGame.getWhitePlayerScore());
            ps.setDouble(5, chessGame.getBlackPlayerScore());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void update(ChessGameNew chessGame) {
        String query = "UPDATE chess_game "
            + "SET "
            + "board_status = ?, "
            + "current_turn_team_color = ?, "
            + "white_player_score = ?, "
            + "black_player_score = ? "
            + "WHERE chess_game_id = ?";
        jdbcTemplate.update(query,
            chessGame.getBoardStatus(),
            chessGame.getCurrentTurnTeamColorValue(),
            chessGame.getWhitePlayerScore(),
            chessGame.getBlackPlayerScore(),
            chessGame.getId());
    }

    @Override
    public List<ChessGameNew> findAll() {
        String query = "SELECT * FROM chess_game";
        return jdbcTemplate.query(query, chessGameRowMapper);
    }

    @Override
    public ChessGameNew findById(Long gameId) {
        String query = "SELECT * FROM chess_game WHERE chess_game_id = ?";
        return jdbcTemplate.queryForObject(query, chessGameRowMapper, gameId);
    }

    @Override
    public void deleteById(Long gameId) {
        String query = "DELETE FROM chess_game WHERE chess_game_id = ?";
        jdbcTemplate.update(query, gameId);
    }
}
