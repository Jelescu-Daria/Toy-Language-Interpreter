package view.console;

import controller.Controller;
import model.ProgramState;
import model.adts.*;
import model.exceptions.InterpreterException;
import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;
import repository.IRepository;
import repository.Repository;
import view.Examples;
import view.console.commands.ExitCommand;
import view.console.commands.RunExample;
import model.types.Type;

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args) throws InterpreterException {

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        Examples examples = new Examples();
        examples.addExamples();

        int index = 1;
        for (IStatement example : examples.getExamples())
        {
            ProgramState prg = new ProgramState(example);
            IRepository repo = new Repository("log" + index + ".txt");
            repo.addProgram(prg);
            Controller controller = new Controller(repo);
            menu.addCommand(new RunExample(Integer.toString(index++), example.toString(), controller));
        }

        menu.show();

    }
}
