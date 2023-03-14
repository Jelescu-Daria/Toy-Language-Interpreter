package view;

import model.adts.MyDictionary;
import model.exceptions.InterpreterException;
import model.expressions.*;
import model.statements.*;
import model.types.*;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;

import java.util.ArrayList;
import java.util.List;

public class Examples {
        List<IStatement> examples;

        public Examples() {
                this.examples = new ArrayList<>();
        }

        private void typecheckAll() throws InterpreterException {
                for (IStatement example : examples)
                        example.typecheck(new MyDictionary<String, Type>());
        }

        public IStatement getAt(int i) {
                return examples.get(i);
        }

        public void addExamples() throws InterpreterException {
                IStatement ex1 = new CompStatement(new VarDeclarationStatement("v", new IntType()),
                        new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                new PrintStatement(new VariableExpression("v"))));
                IStatement ex2 = new CompStatement(new VarDeclarationStatement("a", new IntType()),
                        new CompStatement(new VarDeclarationStatement("b", new IntType()),
                                new CompStatement(new AssignStatement("a",
                                        new ArithmeticExpression("+", new ValueExpression(new IntValue(2)),
                                                new ArithmeticExpression("*", new ValueExpression(new IntValue(3)),
                                                        new ValueExpression(new IntValue(5))))),
                                        new CompStatement(new AssignStatement("b", new ArithmeticExpression("+",
                                                new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                                                new PrintStatement(new VariableExpression("b"))))));
                IStatement ex3 = new CompStatement(new VarDeclarationStatement("a", new BoolType()),
                        new CompStatement(new VarDeclarationStatement("v", new IntType()),
                                new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                        new CompStatement(new IfStatement(new VariableExpression("a"),
                                                new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                                new PrintStatement(new VariableExpression("v"))))));
                IStatement ex4 = new CompStatement(new VarDeclarationStatement("varf", new StringType()),
                        new CompStatement(new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                                new CompStatement(new OpenRFileStatement(new VariableExpression("varf")),
                                        new CompStatement(new VarDeclarationStatement("varc", new IntType()),
                                                new CompStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                        new CompStatement(new PrintStatement(new VariableExpression("varc")),
                                                                new CompStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                        new CompStatement(new PrintStatement(new VariableExpression("varc")),
                                                                                new CloseRFileStatement(new VariableExpression("varf"))))))))));
                IStatement ex5 = new CompStatement(new VarDeclarationStatement("v", new IntType()), new CompStatement(new IfStatement
                        (new RelationalExpression(">=", new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))),
                                new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                        new PrintStatement(new VariableExpression("v"))));
                IStatement ex6 = new CompStatement(new VarDeclarationStatement("v", new RefType(new IntType())),
                        new CompStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                                new CompStatement(new VarDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                        new CompStatement(new NewStatement("a", new VariableExpression("v")),
                                                new CompStatement(new NewStatement("v", new ValueExpression(new IntValue(30))),
                                                        new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))))))));

                IStatement ex7 = new CompStatement(new VarDeclarationStatement("v", new IntType()),
                        new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                                new CompStatement(new WhileStatement(new RelationalExpression
                                        (">", new VariableExpression("v"), new ValueExpression(new IntValue(0))),
                                        new CompStatement(new PrintStatement(new VariableExpression("v")),
                                                new AssignStatement("v", new ArithmeticExpression
                                                        ("-", new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                        new PrintStatement(new VariableExpression("v")))));
                IStatement ex8 = new CompStatement(new VarDeclarationStatement("v", new IntType()),
                        new CompStatement(new VarDeclarationStatement("a", new RefType(new IntType())),
                                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                        new CompStatement(new NewStatement("a", new ValueExpression(new IntValue(22))),
                                                new CompStatement(new ForkStatement(new CompStatement(new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                                        new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                                new CompStatement(new PrintStatement(new VariableExpression("v")),
                                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))),
                                                        new CompStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))))));

                this.examples.add(ex1);
                this.examples.add(ex2);
                this.examples.add(ex3);
                this.examples.add(ex4);
                this.examples.add(ex5);
                this.examples.add(ex6);
                this.examples.add(ex7);
                this.examples.add(ex8);

                this.typecheckAll();

        }

        public List<IStatement> getExamples() {
                return examples;
        }
}
