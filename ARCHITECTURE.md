## Description

## Architecture diagram
|             Presentation             |                        Logic                        |                         Data                        |
|:------------------------------------:|:---------------------------------------------------:|:---------------------------------------------------:|
|     res/layout/activity_main.xml     |     java/com/example/memecards/MainActivity.java    |                  assets/cards.json                  |
|  res/layout/activity_start_game.xml  |  java/com/example/memecards/StartGameActivity.java  |                  assets/events.json                 |
|  res/layout/activity_popup_card.xml  |  java/com/example/memecards/PopupCardActivity.java  |  java/com/example/memedatabase/BattleDeckStub.java  |
| res/layout/activity_card_library.xml | java/com/example/memecards/CardLibraryActivity.java |      java/com/example/memedatabase/DBLoader.java    |
|                                      |       java/com/example/domainobjects/Deck.java      |   java/com/example/memedatabase/EventListStub.java  |
|                                      |      java/com/example/domainobjects/Event.java      |  java/com/example/memedatabase/MasterDeckStub.java  |
|                                      |     java/com/example/domainobjects/EvenList.java    |  java/com/example/memedatabase/PlayerStatsStub.java |
|                                      |     java/com/example/domainobjects/MemeCard.java    |                                                     |
|                                      |      java/com/example/gamelogic/AI_Player.java      |                                                     |
|                                      |      java/com/example/gamelogic/GameEngine.java     |                                                     |
|                                      |                                                     |                                                     |
|                                      |                                                     |                                                     |
|                                      |                                                     |                                                     |
|                                      |                                                     |                                                     |