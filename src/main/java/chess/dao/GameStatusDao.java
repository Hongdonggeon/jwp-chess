package chess.dao;

public interface GameStatusDao {

    void init(int gameId);

    void update(String nowStatus, String nextStatus);

    String getStatus();

    void reset();
}
