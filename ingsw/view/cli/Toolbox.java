package it.polimi.ingsw.view.cli;

final class Toolbox {

    private Toolbox(){}

    //ANSI codes for colored output in terminal
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_BOLD = "\u001B[1m";
    static final String ANSI_BOLD_OFF = "\u001B[22m";
    static final String ANSI_FAINT = "\u001B[2m";
    static final String ANSI_ITALIC = "\u001B[3m";
    static final String ANSI_UNDERLINE = "\u001B[4m";
    static final String ANSI_SLOW_BLINK = "\u001B[5m";
    static final String ANSI_RAPID_BLINK = "\u001B[6m";
    static final String ANSI_REVERSE = "\u001B[7m";
    static final String ANSI_CONCEAL = "\u001B[8m";
    static final String ANSI_CROSSED = "\u001B[9m";
    static final String ANSI_BLACK = "\u001B[20m";
    static final String ANSI_BLACK_FRAKTUR = "\u001B[21m";
    static final String ANSI_NORMAL_INTENSITY = "\u001B[22m";
    static final String ANSI_NORMAL_COLOR = "\u001B[23m";
    static final String ANSI_NOT_ITALIC_NOT_FRAKTUR = "\u001B[24m";
    static final String ANSI_BLACK_UNDERLINE_OFF = "\u001B[25m";
    static final String ANSI_BLINK_OFF = "\u001B[26m";
    static final String ANSI_INVERSE_OFF = "\u001B[27m";
    static final String ANSI_REVEAL = "\u001B[28m";
    static final String ANSI_NOT_CROSSED = "\u001B[29m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_PURPLE = "\u001B[35m";
    static final String ANSI_CYAN = "\u001B[36m";
    static final String ANSI_WHITE = "\u001B[37m";
    static final String BG_ANSI_BLACK = "\u001B[40m";
    static final String BG_ANSI_RED = "\u001B[41m";
    static final String BG_ANSI_GREEN = "\u001B[42m";
    static final String BG_ANSI_YELLOW = "\u001B[43m";
    static final String BG_ANSI_BLUE = "\u001B[44m";
    static final String BG_ANSI_PURPLE = "\u001B[45m";
    static final String BG_ANSI_CYAN = "\u001B[46m";
    static final String BG_ANSI_WHITE = "\u001B[47m";

    static final String banner= """
             ██████   ██████                █████████  █████               ████     ██████   ███          \s
            ░░██████ ██████                ███░░░░░███░░███               ░░███    ███░░███ ░░░           \s
             ░███░█████░███  █████ ████   ░███    ░░░  ░███████    ██████  ░███   ░███ ░░░  ████   ██████ \s
             ░███░░███ ░███ ░░███ ░███    ░░█████████  ░███░░███  ███░░███ ░███  ███████   ░░███  ███░░███\s
             ░███ ░░░  ░███  ░███ ░███     ░░░░░░░░███ ░███ ░███ ░███████  ░███ ░░░███░     ░███ ░███████ \s
             ░███      ░███  ░███ ░███     ███    ░███ ░███ ░███ ░███░░░   ░███   ░███      ░███ ░███░░░  \s
             █████     █████ ░░███████    ░░█████████  ████ █████░░██████  █████  █████     █████░░██████ \s
            ░░░░░     ░░░░░   ░░░░░███     ░░░░░░░░░  ░░░░ ░░░░░  ░░░░░░  ░░░░░  ░░░░░     ░░░░░  ░░░░░░  \s
                              ███ ░███                                                                    \s
                             ░░██████                                                                     \s
                              ░░░░░░                                                                      \s  
                                                                                                                                                                      \s
                """;


    static final String boardStringHeader = """
                                   0        1        2        3        4        5        6        7        8
                             ▞██████████████████████████████████████████████████████████████████████████████████▚\s
                     """;

    static final String boardStringFooter = """
                             █████████████████████████████████████████████████████████████████████████████████████\s
                             ▚███████████████████████████████████████████████████████████████████████████████████▞\s
                     """;

    static final String boardStringSeparator= """
                           █████████████████████████████████████████████████████████████████████████████████████\s 
                   """;

    static final String boardStringLeftEdge= "██";

    static final String boardspacer = "        ";



    static final String bookshelfStringHeader = """
                                  0       1       2       3       4    
                             /██████████████████████████████████████████
                     """;

    static final String getBookshelfString = """
                                  0       1       2       3       4    
                              ██████████████████████████████████████████      
                        0     ██      █▉      ██      ██      ██      ██
                              ██      ██      ██      ██      ██      ██
                              ██████████████████████████████████████████
                        1     ██      ██      ██      ██      ██      ██
                              ██      ██      ██      ██      ██      ██
                              ██████████████████████████████████████████
                        2     ██      ██      ██      ██      ██      ██
                              ██      ██      ██      ██      ██      ██
                              ██████████████████████████████████████████
                        3     ██      ██      ██      ██      ██      ██
                              ██      ██      ██      ██      ██      ██
                              ██████████████████████████████████████████
                        4     ██      ██      ██      ██      ██      ██
                              ██      ██      ██      ██      ██      ██
                              ██████████████████████████████████████████
                        5     ██      ██      ██      ██      ██      ██
                              ██      ██      ██      ██      ██      ██
                             ████████████████████████████████████████████
                           ████████████████████████████████████████████████
                     """;

    static final String bookshelfStringSeparator= """
                           ░██████████████████████████████████████████     \s
                   """;

    static final String bookshelfStringFooter = """
                            ░████████████████████████████████████████████\\
                           ████████████████████████████████████████████████
                     """;

    static final String bookshelfStringLeftEdge= "██";

    static final String bookshelfSpacer = "        ";



    static final String personalCardStringHeader = """
                            0    1    2    3    4           
                         ███████████████████████████
                     """;

    static final String personalCardStringFooter = """ 
                         ██████████████████████████████
                     """;

    static final String personalCardStringSeparator = """
                         ███████████████████████████    
                     """;

    static final String personalCardStringLeftEdge = "██";

    static final String personalCardStringSpacer = "    ";



    static final String file42= """
                                                        ,,.....   .         ..,               ,,..   ...........             .          ....,     \s
                                          ,,.........         .                 .                               ..    . .....            ..,       \s
                                      ,,,.      &@@@&%#&@@@                      .,,                                 ,,,,,,,,,,.       ..          \s
                                  ,... .           @@((((((&@.            ,#&&&&#/.   ..                                                           \s
                                  ,,.....    .......#@((((((%@  ......#@@#((((((((%@@# .                                                           \s
                                         ,,,,,       @#((((((@%.   #@@#((((((((#@@  ,,                                                             \s
                                                     %@(((((((@@@@&(((((((((((@@                                                                   \s
                                                      #@@(((((((((((((((((((&@&                                                                    \s
                                                         @@&((((((((#%&@@@@&                                                                       \s
                                                      @@&((((((@@                                                                                  \s
                                                   %@@((((((((@@                                                                                   \s
                                                 @@%(((((((((@%                                                                                    \s
                                               (@#((((((((((@%                      ,,,.,,                                                         \s
                                              @@(((((((((((@&          ,,,,,,,.          ...,         ,,..,,,    ... ...,                          \s
                                            #@#(((((((((((%@          ,..                    ,     ,..    ..,   ..    ..,        #                 \s
                                           &@(((((((((((((@#           ,. ..                 .,,   ,..   ..      ,,,,  ,       (##                 \s
                                          %@((((((((((((((@                ,,....       ..   ..,       ,,,                     ###                 \s
                                         /@(((((((((((((((@/                    ,,,..,.,,,,   ,                                                    \s
                                         &@(((((((((((((((@%                                                               ##                      \s
                                         @#((((((((((((((((@#                                                              %#    %                 \s
                                         @#(((((((((((((((((@@                                                               ((#((((#              \s
                                         @#(((((((((((((((((((@@@                                                       (/%(# /%%(                 \s
                                         @%(#@@#(((((((((((((((((#&@@@&%#((((//                                      (%&((#%((&(%(%((###((#/       \s
                               &@@@@@&%&@@@#((((((((((((((((((((((((((((((((((((((%@@@#                            /%##&&((#(%##(%#((((%%(((%      \s
                                 &@%(((((((((((((((((((((((((((((((((((((((((((((((((((@@                            &%####%((((%#(#((#%%%%(       \s
                                   #@@&#((((#%&@@(((((((((((((((((((((((((((((((((((((((%@#                          #%%%%@%####%#%######%&(       \s
                                         @%#######(((((((((((((((((((((((((((((((((((((((#@/                          &%#########%######&          \s
                                        /@###(((((((((((((((((((((((((((((((((((((((((((((&&                           #%#############(%           \s
                                        (@###(((((((((((((((((((((((((((((((((((((((((((((#@                              (&#(####(%%              \s
                                        /@%###((((((((((((((((((%@@@@@#(((((((((((((((((((#@                                                       \s
                                         #@%####(((((((((((((((,   @@@&(((((((((((((((((((&&                                                       \s
                                           @@%####(((((((((((((((&@@&(((((((((((((((((((((@#                                                       \s
                                            ,%@@######(((((((((((((((((((((((((((((((((((%@                                                        \s
                                   ,,......     &@@%#########((((#@@&&%%############%&@@@@%                                      ,,,,,,,,,,        \s
                                  ,,......         .%@@&################################@@@                                   ,,.... .......,,     \s
                                         ,,,,...          %@@@@&&%%#########%%&&@@@@@%(                                 ,,,........   ..    ..,,   \s
                                              ,,,.......,,,,,,,                                                      ,,..                        . \s
                                                                                                                     ,,.                         . \s
                                                                                                                   ,...                        ... \s
                                                                                                                    ......,.....,,,,,,,,,,,  ,..  
                            """;



    static final String shyguy = """
            ⠀⠀⠀⠀⠀⠀⢀⣠⣴⣶⣾⣿⣿⠿⠓⠒⠒⠒⠒⠤⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⢀⣤⣾⣿⣿⣿⣿⡿⠋⠀⣀⡀⠀⠀⠀⠀⠀⣀⣉⠢⡀⠀⠀⠀⠀⠀⠀
            ⠀⢀⣴⣿⣿⣿⣿⣿⣿⠏⠀⣰⣿⣿⣿⣿⣦⡀⠀⣾⣿⣿⣷⡜⢆⠀⠀⠀⠀⠀
            ⣠⣿⣿⣿⣿⣿⣿⣿⡏⠀⢰⣿⣿⣿⣿⣿⣿⣷⡀⣿⣿⣿⣿⣿⡌⣆⠀⠀⠀⠀
            ⠙⠻⠿⢿⣿⣿⣿⣿⠁⠀⢸⣿⣿⣿⣿⣿⣿⣿⣇⢹⣿⣿⣿⣿⣷⠸⣄⣠⣤⡀
            ⠀⠀⠀⠀⣿⣿⣿⡿⠀⠀⠈⣿⣿⣿⣿⣿⣿⣿⡿⠀⢻⣿⣿⣿⣿⠀⣿⣿⣿⣷
            ⠀⠀⠀⠀⣿⣿⣿⣇⠀⠀⠀⠘⢿⣿⣿⣿⣿⣿⠇⠀⠀⠙⠿⠿⠋⠀⣿⣿⣿⣿
            ⠀⠀⠀⢸⣿⣿⣿⣿⠀⠀⠀⠀⠈⠙⠻⠿⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⡏
            ⠀⠀⠀⢻⣿⣿⣿⣿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⣄⠀⠀⠀⢀⣿⣿⡟⠀
            ⠀⠀⠀⣨⣿⣿⣿⣿⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⠀⠀⠀⣼⣿⠏⠀⠀
            ⢀⣴⣿⣿⣿⣿⣿⣿⣿⣿⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠁⠀⢀⣼⣿⣿⠀⠀⠀
            ⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⣤⣀⡀⠀⠀⠀⠀⣀⣠⣴⣿⣿⣿⣿⠀⠀⠀
            ⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀       <--- Hi Everybody, this is me :)
            ⠀⠈⠉⠉⣽⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⠉⠁⠀⠈⢹⣿⣿⣿⣿⣄⠀⠀
            ⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣦⣤⣤⣶⣿⣿⣿⣿⣿⣿⠇⠀
            ⠀⠀⣠⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣯⣄⠀
            ⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣇
            ⠀⠸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠏⠉⠉⠙⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇
            ⠀⠀⠙⠻⠿⣿⣿⣿⣿⠿⠿⠟⠛⠁⠀⠀⠀⠀⠀⠉⠛⠿⠿⣿⣿⣿⡿⠿⠛⠀       
            """;



    static final String doge = """
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡟⠋⠈⠙⣦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⠤⢤⡀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀⠀⠈⢇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡠⠞⠀⠀⢠⡜⣦⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡃⠀⠀⠀⠀⠈⢷⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡠⠊⣠⠀⠀⠀⠀⢻⡘⡇
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⠃⠀⠀⠀⠀⠀⠀⠙⢶⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡠⠚⢀⡼⠃⠀⠀⠀⠀⠸⣇⢳
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⠀⣀⠖⠀⠀⠀⠀⠉⠀⠀⠈⠉⠛⠛⡛⢛⠛⢳⡶⠖⠋⠀⢠⡞⠀⠀⠀⠐⠆⠀⠀⣿⢸
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⣦⣀⣴⡟⠀⠀⢶⣶⣾⡿⠀⠀⣿⢸
            ⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⠞⠁⠀⠀⠀⠀⠀⠀⠀⠀⡠⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠻⣏⠀⠀⠀⣶⣿⣿⡇⠀⠀⢏⡞
            ⠀⠀⠀⠀⠀⠀⢀⡴⠛⠀⠀⠀⠀⠀⠀⠀⠀⢀⢀⡾⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⢦⣤⣾⣿⣿⠋⠀⠀⡀⣾⠁
            ⠀⠀⠀⠀⠀⣠⠟⠁⠀⠀⠀⣀⠀⠀⠀⠀⢀⡟⠈⢀⣤⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠙⣏⡁⠀⠐⠚⠃⣿⠀
            ⠀⠀⠀⠀⣴⠋⠀⠀⠀⡴⣿⣿⡟⣷⠀⠀⠊⠀⠴⠛⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠀⠀⠀⠀⢹⡆
            ⠀⠀⠀⣴⠃⠀⠀⠀⠀⣇⣿⣿⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⡶⢶⣶⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇
            ⠀⠀⣸⠃⠀⠀⠀⢠⠀⠊⠛⠉⠁⠀⠀⠀⠀⠀⠀⠀⢲⣾⣿⡏⣾⣿⣿⣿⣿⠖⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢧
            ⠀⢠⡇⠀⠀⠀⠀⠈⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠈⠛⠿⣽⣿⡿⠏⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡜
            ⢀⡿⠀⠀⠀⠀⢀⣤⣶⣟⣶⣦⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇
            ⢸⠇⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇
            ⣼⠀⢀⡀⠀⠀⢷⣿⣿⣿⣿⣿⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⡇
            ⡇⠀⠈⠀⠀⠀⣬⠻⣿⣿⣿⡿⠙⠀⠀⢀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⠁
            ⢹⡀⠀⠀⠀⠈⣿⣶⣿⣿⣝⡛⢳⠭⠍⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⠃⠀
            ⠸⡇⠀⠀⠀⠀⠙⣿⣿⣿⣿⣿⣿⣷⣦⣀⣀⣀⣤⣤⣴⡶⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⠇⠀⠀
            ⠀⢿⡄⠀⠀⠀⠀⠀⠙⣇⠉⠉⠙⠛⠻⠟⠛⠛⠉⠙⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡰⠋⠀⠀⠀
            ⠀⠈⢧⠀⠀⠀⠀⠀⠀⠈⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⠞⠁⠀⠀⠀⠀
            ⠀⠀⠘⢷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⠞⠁⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠱⢆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⡴⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠛⢦⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣠⠴⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠈⠛⠲⠤⣤⣤⣤⣄⠀⠀⠀⠀⠀⠀⠀⢠⣤⣤⠤⠴⠒⠛⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            """;



    static final String rules= """ 
                  Goal                                                   Rulebook 
                  Players take item tiles from the living room and place them in
                  their bookshelves to score points; the game ends when a player
                  completely fills their bookshelf. The player with more points at
                  the end will win the game. There are 4 ways to score points:
                              
                   Personal Goal card: The personal goal card grants points
                                       if you match the highlighted spaces
                                       with the corresponding item tiles.
                                       
                   Common Goal cards: The common goal cards grant points to
                                      the players who achieve the illustrated
                                      pattern. See the last page for a detailed
                                      descriptions of the common goal cards.
                   Adjacent Item tiles: Groups of adjacent item tiles of the
                                        same type on your bookshelf grant
                                        points depending on how many
                                        tiles are connected (with one side
                                        touching).
                   Game-end trigger: The first player who completely
                                     fills their bookshelf scores 1
                                     additional point. 
                   _______________________________________________________________
                   
                   GamePlay                                               Rulebook                  
                   The game is divided in turns.
                   During your turn, you must take 1, 2 or 3 item tiles from the 
                   living room board, following these rules:
                   The tiles you take must be adjacent to each other and form a 
                   straight line.
                   All the tiles you take must have at least one side free (not 
                   touching directly other tiles) at the beginning of your turn 
                   (i.e. you cannot take a tile that becomes free after your first
                   pick). Then, you must decide the order and a column of your
                   bookshelf to put the tiles in. If in the board there are only 
                   tiles without any other adjacent tile, or the board is empty, 
                   itwill be automatically refilled.
                   _______________________________________________________________
                   
                   Game End                                               Rulebook
                   The first player who fills all the spaces of their bookshelf 
                   takes the end game token. This declare the last round: the game
                   continues until all players have played the same number of 
                   turns. The player who scored most points wins the game.
                   _______________________________________________________________
                   
                   Scoring                                               Rulebook                   
                   
                   
                   Personal Goal card: 1/2/4/6/9/12 points for 1/2/3/4/5/6 item 
                                       tiles in the exact position illustrated by
                                       their personal goal card.
                                       
                   Common Goal cards: 8/6/4/2 points for the 1°/2°/3°/4° player 
                                      to complete the Goal.
                                        
                   Adjacent Item tiles: 2/3/5/8 points for groups of 3/4/5/6+ 
                                        item tiles of the same type adjacent on
                                        their bookshelf. .
                                        
                   Game-end trigger: The first player who completely
                                     fills their bookshelf scores 1
                                     additional point. 
            """;



    static final String boardStringHeaderOld = """
                                    0          1          2          3          4          5          6          7          8
                            .^~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~^.   \s
                             :~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~^    \s
                     """;
    static final String boardStringFooterOld = """
                             .:^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:.    \s
                           .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~^.  \s
                           :~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~:  \s
           
                     """;
    static final String boardStringSeparatorOld= """
                            ^~~^^^^^^^^~~~^^^^^^^^~~~^^^^^^^^~~~^^^^^^^^~~~^^^^^^^^~~~^^^^^^^^~~~^^^^^^^^~~~^^^^^^^^~~~^^^^^^^^~~^     \s 
                   """;
    static final String boardStringLeftEdgeOld= "~~~";
    static final String boardspacerOld = "        .";
    static final String bookshelfStringHeaderOld = """
                                    0          1          2          3          4
                            .^~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~^.   \s
                             :~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~^    \s
                     """;
    static final String bookshelfStringOld = """
                        0    .~~:       :~~^       .~~~        ^~~.       ^~~:       :~~:    \s
                             .~~^       :~~^       .~~~        ^~~.       ^~~:       :~~:    \s
                             .~~~^^^^^^:^~~~^:^^^^:^~~~^:^^^^:^~~~^:::::::~~~^:::::::^~~.    \s
                        1     ~~^       :~~^       .~~~.       ~~~.       ^~~:       :~~.    \s
                              ~~^       .~~^        ~~~        ~~~.       ^~~.       ^~~.    \s
                             .~~~^^^^^^:^~~~^:^^^^:^~~~^:^^^^:^~~~^:::::::~~~^:::::::^~~.    \s
                        2     ^~~       .~~~        ~~~        ~~~        ~~~.       ^~~     \s
                              ^~~.      .~~~.      .~~~.       ~~~.       ~~~.       ^~^     \s
                              ^~~^^^^^^^^~~~^^^^^^^^~~~^^^^^^^^~~~^^^^^^^^~~~^^^^^^^^~~^     \s
                        3     :~~.       ~~~.       ~~~        ~~~        ~~~.       ~~^     \s
                              :~~.       ~~~.       ~~~        ~~~        ~~~        ~~^     \s
                             .~~~^^^^^^:^~~~^:^^^^:^~~~^:^^^^:^~~~^:::::::~~~^:::::::^~~.    \s
                        4     :~~:       ~~~.       ~~~        ~~~       .~~~       .~~:     \s
                              :~~.       ~~~.       ~~~        ~~~        ~~~        ~~^     \s
                             .~~~^^^^^^:^~~~^:^^^^:^~~~^:^^^^:^~~~^:::::::~~~^:::::::^~~.    \s
                        5     .~~:       ^~~.       ~~~        ~~^       .~~^       :~~.     \s
                              .~~:       ^~~:      .~~~.      .~~^       .~~^       :~~.     \s
                     """;

    static final String bookshelfStringLeftEdgeOld= "~~~";
    static final String bookshelfStringSeparatorOld= """
                            ^~~^^^^^^^^~~~^^^^^^^^~~~^^^^^^^^~~~^^^^^^^^~~~^^^^^^^^~~^     \s 
                   """;
    static final String bookshelfStringFooterOld = """
                             .:^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:.    \s
                           .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~^.  \s
                           :~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~:  \s
                         .~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~^ \s
                        :^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^~^.
           
                     """;
    static final String personalCardStringHeaderOld = """
                      0       1       2       3       4
                ###############################################
                ###!!!!!!!#!!!!!!!!#!!!!!!!!#!!!!!!!#!!!!!!!###
            """;
    static final String personalCardStringFooterOld = """ 
                ###!!!!!!!#!!!!!!!!#!!!!!!!!#!!!!!!!#!!!!!!!###
                ###############################################
            """;
    static final String personalCardStringSeparatorOld = """
                ###!~~~~~~~!~~~~~~~!~~~~~~~!~~~~~~~!~~~~~~~!###
            """;
    static final String personalCardStringLeftEdgeOld = "###!:";

    static final String personalCardStringSpacerOld = "    ";
}
