package english.tenses.practice.parser;

import english.tenses.practice.parser.compiler.CompilerKt;
import english.tenses.practice.parser.compiler.NewCompilerKt;
import english.tenses.practice.parser.editor.EditorKt;
import english.tenses.practice.parser.handler.HandlerKt;
import english.tenses.practice.parser.raw.RawLoaderKt;

public class All {
    public static void main() {
        RawLoaderKt.main();
        HandlerKt.main();
        EditorKt.main();
        NewCompilerKt.main();
    }
}
