package org.sonar.samples.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;

import java.util.Arrays;
import java.util.List;

@Rule(key = "MyFirstCustomRule",
        name = "Use java.lang.invoke.MethodHandles.Lookup.defineClass instead of sun.misc.Unsafe.defineClass",
        description = "Usage of sun.misc.Unsafe.defineClass (deprecated in Java 9) should be replaced with \n" +
                "java.lang.invoke.MethodHandles.Lookup.defineClass",
        priority = Priority.CRITICAL,
        tags = {"bug"})
public class MyFirstCustomCheck extends IssuableSubscriptionVisitor {

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        if (hasSemantic()) {
            if (tree.is(Tree.Kind.METHOD_INVOCATION)) {
                MethodInvocationTree methodInvocationTree = (MethodInvocationTree) tree;
                ExpressionTree expressionTree = methodInvocationTree.methodSelect();

                List<IdentifierTree> usages = methodInvocationTree.symbol().usages();
                if (methodInvocationTree.symbol().usages().get(0).name().equals("defineClass")) {
                    ExpressionTree expression = ((MemberSelectExpressionTree) expressionTree).expression();
                    if (expression.symbolType().fullyQualifiedName().equals("sun.misc.Unsafe")) {
                        reportIssue(tree, "sun.misc.Unsafe.defineClass is deprecated use java.lang.invoke.MethodHandles.Lookup.defineClass instead");
                    }
                }
            }
        }
    }
}
