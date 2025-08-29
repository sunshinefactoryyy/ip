package bobbot.parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import bobbot.parser.Parser;

public class ParserTest {
    @Test
    public void testParseCommand() {
        Parser.Command result = Parser.parseCommand("todo read book");
        assertEquals(Parser.CommandType.TODO, result.getType());
        assertEquals("read book", result.getArguments()[0]);
        
        Parser.Command invalidResult = Parser.parseCommand("invalid command");
        assertEquals(Parser.CommandType.INVALID, invalidResult.getType());
        
        Parser.Command byeResult = Parser.parseCommand("bye");
        assertEquals(Parser.CommandType.BYE, byeResult.getType());
    }
}