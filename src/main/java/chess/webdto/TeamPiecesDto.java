package chess.webdto;

import java.util.List;
import java.util.Map;

public class TeamPiecesDto {
    private Map<String,String> white;
    private Map<String,String> black;

    public TeamPiecesDto(Map<String,String> white, Map<String,String> black) {
        this.white = white;
        this.black = black;
    }

    public Map<String,String> getWhite() {
        return white;
    }

    public Map<String,String> getBlack() {
        return black;
    }
}
