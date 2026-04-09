def ec = org.moqui.context.ExecutionContextFactory.getExecutionContext()
def components = ec.getComponentNames()
println "COMPONENTS: " + components
