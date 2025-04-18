package bg.sofia.uni.fmi.myfitnesspal.commands;

import bg.sofia.uni.fmi.myfitnesspal.Controller;
import bg.sofia.uni.fmi.myfitnesspal.items.Consumable;
import bg.sofia.uni.fmi.myfitnesspal.items.Water;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandFactory {
    private final Map<String, Command> commands = new HashMap<>();
    private final Controller controller;
    private final CommandValidator commandValidator;

    public CommandFactory(Map<String, Consumable> items, Scanner scanner,
                          Controller controller) {
        this.controller = controller;

        Command drinkWater =
                new DrinkWaterCommand((Water) items.get("water"), scanner);
        Command checkWater =
                new CheckWaterCommand((Water) items.get("water"), scanner);
        Command createMenu = new CreateMealCommand(controller, scanner);
        Command consumeMeal = new ConsumeMealCommand(controller, scanner);
        Command createFood = new CreateFoodCommand(scanner, controller);
        Command viewAllFood = new ViewAllFoodsCommand(controller);
        Command logFood = new LogFoodCommand(controller, scanner);
        Command viewAllLogged = new ViewAllLoggedCommand(controller, scanner);
        Command exit = new ExitCommand();

        commands.put(drinkWater.toString(), drinkWater);
        commands.put(checkWater.toString(), checkWater);
        commands.put(createFood.toString(), createFood);
        commands.put(viewAllFood.toString(), viewAllFood);
        commands.put(logFood.toString(), logFood);
        commands.put(createMenu.toString(), createMenu);
        commands.put(consumeMeal.toString(), consumeMeal);
        commands.put(viewAllLogged.toString(), viewAllLogged);
        commands.put(exit.toString(), exit);

        commandValidator = new CommandValidator(commands.keySet());
    }

    public Command getCommand(String commandName) {
        if (!commandValidator.isValidCommand(commandName)) {
            throw new IllegalArgumentException("this is not a valid command");
        }
        String normalizedCommand = commandName.trim().toLowerCase();
        return commands.getOrDefault(normalizedCommand, () -> null);
    }

}
