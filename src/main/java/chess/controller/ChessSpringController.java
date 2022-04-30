package chess.controller;

import chess.domain.board.strategy.WebBasicBoardStrategy;
import chess.dto.ErrorDto;
import chess.dto.GameDto;
import chess.dto.GameStatusDto;
import chess.dto.MoveDto;
import chess.dto.ScoreDto;
import chess.service.ChessGameService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChessSpringController {

    private final ChessGameService chessGameService;

    public ChessSpringController(ChessGameService chessGameService) {
        this.chessGameService = chessGameService;
    }

    @GetMapping("/")
    public String enter(Model model) {
        List<GameDto> gameDtos = chessGameService.find();
        model.addAttribute("games", gameDtos);
        return "home";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute GameDto gameDto) {
        chessGameService.create(gameDto);
        return "redirect:/";
    }

    @GetMapping("/game/{gameId}")
    public String enter(@PathVariable int gameId) {
        chessGameService.init(gameId);
        return "index";
    }

    @PostMapping("/game/{gameId}/delete")
    public String delete(@RequestParam("password") String password, @PathVariable int gameId) {
        chessGameService.delete(gameId, password);
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/game/{gameId}/start")
    public GameStatusDto start(@PathVariable int gameId) {
        return chessGameService.startChessGame(new WebBasicBoardStrategy(), gameId);
    }

    @ResponseBody
    @PostMapping("/game/{gameId}/move")
    public ResponseEntity move(@RequestBody MoveDto moveDto, @PathVariable int gameId) {
        GameStatusDto gameStatusDto = chessGameService.move(moveDto.getFrom(), moveDto.getTo(), gameId);
        return ResponseEntity.ok().body(gameStatusDto);
    }

    @ResponseBody
    @GetMapping("/game/{gameId}/status")
    public ResponseEntity status(@PathVariable int gameId) {
        ScoreDto scoreDto = chessGameService.createScore(gameId);
        return ResponseEntity.ok().body(scoreDto);
    }

    @ResponseBody
    @GetMapping("/game/{gameId}/end")
    public ResponseEntity end(@PathVariable int gameId) {
        ScoreDto scoreDto = chessGameService.end(gameId);
        ;
        return ResponseEntity.ok().body(scoreDto);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handle(IllegalArgumentException illegalArgumentException) {
        return ResponseEntity.internalServerError().body(new ErrorDto(illegalArgumentException.getMessage()));
    }
}
