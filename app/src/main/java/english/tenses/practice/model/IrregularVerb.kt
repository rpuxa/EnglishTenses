package english.tenses.practice.model

enum class IrregularVerb(
    val first: String,
    val second: List<String>,
    val third: List<String>
) {
    ARISE("arise", listOf("arose"), listOf("arisen")),
    AWAKE("awake", listOf("awakened", "awoke"), listOf("awakened", "awoken")),
    BACKSLIDE("backslide", listOf("backslid"), listOf("backslidden", "backslid")),
    BE("be", listOf("was, were"), listOf("been")),
    BEAR("bear", listOf("bore"), listOf("born", "borne")),
    BEAT("beat", listOf("beat"), listOf("beaten", "beat")),
    BECOME("become", listOf("became"), listOf("become")),
    BEGIN("begin", listOf("began"), listOf("begun")),
    BEND("bend", listOf("bent"), listOf("bent")),
    BET("bet", listOf("bet", "betted"), listOf("bet", "betted")),
    BIND("bind", listOf("bound"), listOf("bound")),
    BITE("bite", listOf("bit"), listOf("bitten")),
    BLEED("bleed", listOf("bled"), listOf("bled")),
    BLOW("blow", listOf("blew"), listOf("blown")),
    BREAK("break", listOf("broke"), listOf("broken")),
    BREED("breed", listOf("bred"), listOf("bred")),
    BRING("bring", listOf("brought"), listOf("brought")),
    BROADCAST("broadcast", listOf("broadcast", "broadcasted"), listOf("broadcast", "broadcasted")),
    BROWBEAT("browbeat", listOf("browbeat"), listOf("browbeaten", "browbeat")),
    BUILD("build", listOf("built"), listOf("built")),
    BURN("burn", listOf("burned", "burnt"), listOf("burned", "burnt")),
    BURST("burst", listOf("burst"), listOf("burst")),
    BUST("bust", listOf("busted", "bust"), listOf("busted", "bust")),
    BUY("buy", listOf("bought"), listOf("bought")),
    CAN("can", listOf("could"), listOf("could")),
    CAST("cast", listOf("cast"), listOf("cast")),
    CATCH("catch", listOf("caught"), listOf("caught")),
    CHOOSE("choose", listOf("chose"), listOf("chosen")),
    CLING("cling", listOf("clung"), listOf("clung")),
    CLOTHE("clothe", listOf("clothed", "clad"), listOf("clothed", "clad")),
    COME("come", listOf("came"), listOf("come")),
    COST("cost", listOf("cost"), listOf("cost")),
    CREEP("creep", listOf("crept"), listOf("crept")),
    CUT("cut", listOf("cut"), listOf("cut")),
    DEAL("deal", listOf("dealt"), listOf("dealt")),
    DIG("dig", listOf("dug"), listOf("dug")),
    DIVE("dive", listOf("dove", "dived"), listOf("dived")),
    DO("do", listOf("did"), listOf("done")),
    DRAW("draw", listOf("drew"), listOf("drawn")),
    DREAM("dream", listOf("dreamed", "dreamt"), listOf("dreamed", "dreamt")),
    DRINK("drink", listOf("drank"), listOf("drunk")),
    DRIVE("drive", listOf("drove"), listOf("driven")),
    DWELL("dwell", listOf("dwelt", "dwelled"), listOf("dwelt", "dwelled")),
    EAT("eat", listOf("ate"), listOf("eaten")),
    FALL("fall", listOf("fell"), listOf("fallen")),
    FEED("feed", listOf("fed"), listOf("fed")),
    FEEL("feel", listOf("felt"), listOf("felt")),
    FIGHT("fight", listOf("fought"), listOf("fought")),
    FIND("find", listOf("found"), listOf("found")),
    FIT("fit", listOf("fit"), listOf("fit")),
    FLEE("flee", listOf("fled"), listOf("fled")),
    FLING("fling", listOf("flung"), listOf("flung")),
    FLY("fly", listOf("flew"), listOf("flown")),
    FORBID("forbid", listOf("forbade"), listOf("forbidden")),
    FORECAST("forecast", listOf("forecast"), listOf("forecast")),
    FORESEE("foresee", listOf("foresaw"), listOf("foreseen")),
    FORETELL("foretell", listOf("foretold"), listOf("foretold")),
    FORGET("forget", listOf("forgot"), listOf("forgotten")),
    FORGIVE("forgive", listOf("forgave"), listOf("forgiven")),
    FORSAKE("forsake", listOf("forsook"), listOf("forsaken")),
    FREEZE("freeze", listOf("froze"), listOf("frozen")),
    GET("get", listOf("got"), listOf("gotten", "got")),
    GIVE("give", listOf("gave"), listOf("given")),
    GO("go", listOf("went"), listOf("gone")),
    GRIND("grind", listOf("ground"), listOf("ground")),
    GROW("grow", listOf("grew"), listOf("grown")),
    HANG("hang", listOf("hung", "hanged"), listOf("hung", "hanged")),
    HAS("have", listOf("had"), listOf("had")),
    HEAR("hear", listOf("heard"), listOf("heard")),
    HEW("hew", listOf("hewed"), listOf("hewn", "hewed")),
    HIDE("hide", listOf("hid"), listOf("hidden")),
    HIT("hit", listOf("hit"), listOf("hit")),
    HOLD("hold", listOf("held"), listOf("held")),
    HURT("hurt", listOf("hurt"), listOf("hurt")),
    INLAY("inlay", listOf("inlaid"), listOf("inlaid")),
    INPUT("input", listOf("input", "inputted"), listOf("input", "inputted")),
    INTERWEAVE("interweave", listOf("interwove"), listOf("interwoven")),
    KEEP("keep", listOf("kept"), listOf("kept")),
    KNEEL("kneel", listOf("knelt", "kneeled"), listOf("knelt", "kneeled")),
    KNIT("knit", listOf("knitted", "knit"), listOf("knitted", "knit")),
    KNOW("know", listOf("knew"), listOf("known")),
    LAY("lay", listOf("laid"), listOf("laid")),
    LEAD("lead", listOf("led"), listOf("led")),
    LEAN("lean", listOf("leaned", "leant"), listOf("leaned", "leant")),
    LEAP("leap", listOf("leaped", "leapt"), listOf("leaped", "leapt")),
    LEARN("learn", listOf("learnt", "learned"), listOf("learnt", "learned")),
    LEAVE("leave", listOf("left"), listOf("left")),
    LEND("lend", listOf("lent"), listOf("lent")),
    LET("let", listOf("let"), listOf("let")),
    LIE("lie", listOf("lay"), listOf("lain")),
    LIGHT("light", listOf("lit", "lighted"), listOf("lit", "lighted")),
    LOSE("lose", listOf("lost"), listOf("lost")),
    MAKE("make", listOf("made"), listOf("made")),
    MAY("may", listOf("might"), listOf("might")),
    MEAN("mean", listOf("meant"), listOf("meant")),
    MEET("meet", listOf("met"), listOf("met")),
    MISCAST("miscast", listOf("miscast"), listOf("miscast")),
    MISDEAL("misdeal", listOf("misdealt"), listOf("misdealt")),
    MISDO("misdo", listOf("misdid"), listOf("misdone")),
    MISGIVE("misgive", listOf("misgave"), listOf("misgiven")),
    MISHEAR("mishear", listOf("misheard"), listOf("misheard")),
    MISHIT("mishit", listOf("mishit"), listOf("mishit")),
    MISLAY("mislay", listOf("mislaid"), listOf("mislaid")),
    MISLEAD("mislead", listOf("misled"), listOf("misled")),
    MISREAD("misread", listOf("misread"), listOf("misread")),
    MISSPELL("misspell", listOf("misspelled", "misspelt"), listOf("misspelled", "misspelt")),
    MISSPEND("misspend", listOf("misspent"), listOf("misspent")),
    MISTAKE("mistake", listOf("mistook"), listOf("mistaken")),
    MISUNDERSTAND("misunderstand", listOf("misunderstood"), listOf("misunderstood")),
    MOW("mow", listOf("mowed"), listOf("mowed", "mown")),
    OFFSET("offset", listOf("offset"), listOf("offset")),
    OUTBID("outbid", listOf("outbid"), listOf("outbid")),
    OUTDO("outdo", listOf("outdid"), listOf("outdone")),
    OUTFIGHT("outfight", listOf("outfought"), listOf("outfought")),
    OUTGROW("outgrow", listOf("outgrew"), listOf("outgrown")),
    OUTPUT("output", listOf("output", "outputted"), listOf("output", "outputted")),
    OUTRUN("outrun", listOf("outran"), listOf("outrun")),
    OUTSELL("outsell", listOf("outsold"), listOf("outsold")),
    OUTSHINE("outshine", listOf("outshone"), listOf("outshone")),
    OVERBID("overbid", listOf("overbid"), listOf("overbid")),
    OVERCOME("overcome", listOf("overcame"), listOf("overcome")),
    OVERDO("overdo", listOf("overdid"), listOf("overdone")),
    OVERDRAW("overdraw", listOf("overdrew"), listOf("overdrawn")),
    OVEREAT("overeat", listOf("overate"), listOf("overeaten")),
    OVERFLY("overfly", listOf("overflew"), listOf("overflown")),
    OVERHANG("overhang", listOf("overhung"), listOf("overhung")),
    OVERHEAR("overhear", listOf("overheard"), listOf("overheard")),
    OVERLAY("overlay", listOf("overlaid"), listOf("overlaid")),
    OVERPAY("overpay", listOf("overpaid"), listOf("overpaid")),
    OVERRIDE("override", listOf("overrode"), listOf("overridden")),
    OVERRUN("overrun", listOf("overran"), listOf("overrun")),
    OVERSEE("oversee", listOf("oversaw"), listOf("overseen")),
    OVERSHOOT("overshoot", listOf("overshot"), listOf("overshot")),
    OVERSLEEP("oversleep", listOf("overslept"), listOf("overslept")),
    OVERTAKE("overtake", listOf("overtook"), listOf("overtaken")),
    OVERTHROW("overthrow", listOf("overthrew"), listOf("overthrown")),
    PARTAKE("partake", listOf("partook"), listOf("partaken")),
    PAY("pay", listOf("paid"), listOf("paid")),
    PLEAD("plead", listOf("pleaded", "pled"), listOf("pleaded", "pled")),
    PREPAY("prepay", listOf("prepaid"), listOf("prepaid")),
    PROVE("prove", listOf("proved"), listOf("proven", "proved")),
    PUT("put", listOf("put"), listOf("put")),
    QUIT("quit", listOf("quit", "quitted"), listOf("quit", "quitted")),
    READ("read", listOf("read"), listOf("read")),
    REBIND("rebind", listOf("rebound"), listOf("rebound")),
    REBUILD("rebuild", listOf("rebuilt"), listOf("rebuilt")),
    RECAST("recast", listOf("recast"), listOf("recast")),
    REDO("redo", listOf("redid"), listOf("redone")),
    REHEAR("rehear", listOf("reheard"), listOf("reheard")),
    REMAKE("remake", listOf("remade"), listOf("remade")),
    REND("rend", listOf("rent"), listOf("rent")),
    REPAY("repay", listOf("repaid"), listOf("repaid")),
    RERUN("rerun", listOf("reran"), listOf("rerun")),
    RESELL("resell", listOf("resold"), listOf("resold")),
    RESET("reset", listOf("reset"), listOf("reset")),
    RESIT("resit", listOf("resat"), listOf("resat")),
    RETAKE("retake", listOf("retook"), listOf("retaken")),
    RETELL("retell", listOf("retold"), listOf("retold")),
    REWRITE("rewrite", listOf("rewrote"), listOf("rewritten")),
    RID("rid", listOf("rid"), listOf("rid")),
    RIDE("ride", listOf("rode"), listOf("ridden")),
    RING("ring", listOf("rang"), listOf("rung")),
    RISE("rise", listOf("rose"), listOf("risen")),
    RUN("run", listOf("ran"), listOf("run")),
    SAW("saw", listOf("sawed"), listOf("sawed", "sawn")),
    SAY("say", listOf("said"), listOf("said")),
    SEE("see", listOf("saw"), listOf("seen")),
    SEEK("seek", listOf("sought"), listOf("sought")),
    SELL("sell", listOf("sold"), listOf("sold")),
    SEND("send", listOf("sent"), listOf("sent")),
    SET("set", listOf("set"), listOf("set")),
    SEW("sew", listOf("sewed"), listOf("sewn", "sewed")),
    SHAKE("shake", listOf("shook"), listOf("shaken")),
    SHAVE("shave", listOf("shaved"), listOf("shaved", "shaven")),
    SHEAR("shear", listOf("sheared"), listOf("sheared", "shorn")),
    SHED("shed", listOf("shed"), listOf("shed")),
    SHINE("shine", listOf("shined", "shone"), listOf("shined", "shone")),
    SHOOT("shoot", listOf("shot"), listOf("shot")),
    SHOW("show", listOf("showed"), listOf("shown", "showed")),
    SHRINK("shrink", listOf("shrank", "shrunk"), listOf("shrunk")),
    SHUT("shut", listOf("shut"), listOf("shut")),
    SING("sing", listOf("sang"), listOf("sung")),
    SINK("sink", listOf("sank", "sunk"), listOf("sunk")),
    SIT("sit", listOf("sat"), listOf("sat")),
    SLAY("slay", listOf("slew", "slayed"), listOf("slain", "slayed")),
    SLEEP("sleep", listOf("slept"), listOf("slept")),
    SLIDE("slide", listOf("slid"), listOf("slid")),
    SLING("sling", listOf("slung"), listOf("slung")),
    SLINK("slink", listOf("slunk"), listOf("slunk")),
    SLIT("slit", listOf("slit"), listOf("slit")),
    SMELL("smell", listOf("smelled", "smelt"), listOf("smelled", "smelt")),
    SOW("sow", listOf("sowed"), listOf("sown", "sowed")),
    SPEAK("speak", listOf("spoke"), listOf("spoken")),
    SPEED("speed", listOf("sped", "speeded"), listOf("sped", "speeded")),
    SPELL("spell", listOf("spelled", "spelt"), listOf("spelled", "spelt")),
    SPEND("spend", listOf("spent"), listOf("spent")),
    SPILL("spill", listOf("spilled", "spilt"), listOf("spilled", "spilt")),
    SPIN("spin", listOf("spun"), listOf("spun")),
    SPIT("spit", listOf("spit", "spat"), listOf("spit", "spat")),
    SPLIT("split", listOf("split"), listOf("split")),
    SPOIL("spoil", listOf("spoiled", "spoilt"), listOf("spoiled", "spoilt")),
    SPREAD("spread", listOf("spread"), listOf("spread")),
    SPRING("spring", listOf("sprang", "sprung"), listOf("sprung")),
    STAND("stand", listOf("stood"), listOf("stood")),
    STEAL("steal", listOf("stole"), listOf("stolen")),
    STICK("stick", listOf("stuck"), listOf("stuck")),
    STING("sting", listOf("stung"), listOf("stung")),
    STINK("stink", listOf("stunk", "stank"), listOf("stunk")),
    STREW("strew", listOf("strewed"), listOf("strewn", "strewed")),
    STRIDE("stride", listOf("strode"), listOf("stridden")),
    STRIKE("strike", listOf("struck"), listOf("struck")),
    STRING("string", listOf("strung"), listOf("strung")),
    STRIVE("strive", listOf("strove", "strived"), listOf("striven", "strived")),
    SUBLET("sublet", listOf("sublet"), listOf("sublet")),
    SWEAR("swear", listOf("swore"), listOf("sworn")),
    SWEEP("sweep", listOf("swept"), listOf("swept")),
    SWELL("swell", listOf("swelled"), listOf("swollen", "swelled")),
    SWIM("swim", listOf("swam"), listOf("swum")),
    SWING("swing", listOf("swung"), listOf("swung")),
    TAKE("take", listOf("took"), listOf("taken")),
    TEACH("teach", listOf("taught"), listOf("taught")),
    TEAR("tear", listOf("tore"), listOf("torn")),
    TELL("tell", listOf("told"), listOf("told")),
    THINK("think", listOf("thought"), listOf("thought")),
    THROW("throw", listOf("threw"), listOf("thrown")),
    THRUST("thrust", listOf("thrust"), listOf("thrust")),
    TREAD("tread", listOf("trod"), listOf("trodden", "trod")),
    UNBEND("unbend", listOf("unbent"), listOf("unbent")),
    UNDERBID("underbid", listOf("underbid"), listOf("underbid")),
    UNDERCUT("undercut", listOf("undercut"), listOf("undercut")),
    UNDERGO("undergo", listOf("underwent"), listOf("undergone")),
    UNDERLIE("underlie", listOf("underlay"), listOf("underlain")),
    UNDERPAY("underpay", listOf("underpaid"), listOf("underpaid")),
    UNDERSELL("undersell", listOf("undersold"), listOf("undersold")),
    UNDERSTAND("understand", listOf("understood"), listOf("understood")),
    UNDERTAKE("undertake", listOf("undertook"), listOf("undertaken")),
    UNDERWRITE("underwrite", listOf("underwrote"), listOf("underwritten")),
    UNDO("undo", listOf("undid"), listOf("undone")),
    UNFREEZE("unfreeze", listOf("unfroze"), listOf("unfrozen")),
    UNSAY("unsay", listOf("unsaid"), listOf("unsaid")),
    UNWIND("unwind", listOf("unwound"), listOf("unwound")),
    UPHOLD("uphold", listOf("upheld"), listOf("upheld")),
    UPSET("upset", listOf("upset"), listOf("upset")),
    WAKE("wake", listOf("woke", "waked"), listOf("woken", "waked")),
    WAYLAY("waylay", listOf("waylaid"), listOf("waylaid")),
    WEAR("wear", listOf("wore"), listOf("worn")),
    WEAVE("weave", listOf("wove", "weaved"), listOf("woven", "weaved")),
    WED("wed", listOf("wed", "wedded"), listOf("wed", "wedded")),
    WEEP("weep", listOf("wept"), listOf("wept")),
    WET("wet", listOf("wet", "wetted"), listOf("wet", "wetted")),
    WIN("win", listOf("won"), listOf("won")),
    WIND("wind", listOf("wound"), listOf("wound")),
    WITHDRAW("withdraw", listOf("withdrew"), listOf("withdrawn")),
    WITHHOLD("withhold", listOf("withheld"), listOf("withheld")),
    WITHSTAND("withstand", listOf("withstood"), listOf("withstood")),
    WRING("wring", listOf("wrung"), listOf("wrung")),
    WRITE("write", listOf("wrote"), listOf("written")),

    ;

    val secondText = second.txt()
    val thirdText = third.txt()

    private fun List<String>.txt() = joinToString(" / ")

    companion object {
        private val map = values().map {
            it.first to it
        }.toMap()

        fun byFirst(first: String) = map[first]

        fun secondForm(verb: String, person: Person): String? {
            return when (verb) {
                "be" -> if (person == Person.YOU) "were" else "was"
                else -> byFirst(verb)?.second?.first()
            }
        }
    }
}