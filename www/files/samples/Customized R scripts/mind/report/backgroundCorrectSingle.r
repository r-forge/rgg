#copied from the function backgroundCorrect but removed the part where there are
#calculations done in the red channel. Result: The script doesn't have to do the
#calculation twice for a 'dummy' column.
backgroundCorrectSingle = function (RG, method = "subtract", offset = 0, printer = RG$printer,
    verbose = TRUE)
{
	 if (!is.list(RG) && is.vector(RG))
        RG = as.matrix(RG)
    if (is.matrix(RG)) {
        method = match.arg(method, c("none", "normexp", "saddle",
            "neldermean", "bfgs", "rma", "mcgee"))
        if (method == "normexp")
            method = "saddle"
        if (method != "none") {
            for (j in 1:ncol(RG)) {
                x = RG[, j]
                out = normexp.fit(x, method = method)
                RG[, j] = normexp.signal(out$par, x)
                if (verbose)
                  cat("Corrected array", j, "\n")
            }
        }
        if (offset)
            RG = RG + offset
        return(RG)
    }
    method = match.arg(method, c("none", "subtract", "half",
        "minimum", "movingmin", "edwards", "normexp", "saddle",
        "neldermean", "bfgs", "rma", "mcgee"))
    if (is.null(RG$Rb) && is.null(RG$Gb))
        method = "none"
    switch(method, subtract = {
        RG$G = RG$G - RG$Gb
    }, half = {
        RG$G = pmax(RG$G - RG$Gb, 0.5)
    }, minimum = {
        RG$G = as.matrix(RG$G - RG$Gb)
        for (slide in 1:ncol(RG$R)) {
            i = RG$G[, slide] < 1e-18
            if (any(i, na.rm = TRUE)) {
                m = min(RG$G[!i, slide], na.rm = TRUE)
                RG$G[i, slide] = m/2
            }
        }
    }, movingmin = {
        RG$G = RG$G - ma3x3.spottedarray(RG$Gb, printer = printer,
            FUN = min, na.rm = TRUE)
    }, edwards = {
        one = matrix(1, NROW(RG$G), 1)
        delta.vec = function(d, f = 0.1) {
            quantile(d, mean(d < 1e-16, na.rm = TRUE) * (1 +
                f), na.rm = TRUE)
        }
        sub = as.matrix(RG$G - RG$Gb)
        delta = one %*% apply(sub, 2, delta.vec)
        RG$G = ifelse(sub < delta, delta * exp(1 - (RG$Gb +
            delta)/RG$G), sub)
    }, normexp = , saddle = , neldermean = , bfgs = , rma = ,
        mcgee = {
            if (verbose)
                cat("Channel\n")
            RG$G = backgroundCorrectSingle(RG$G - RG$Gb, method = method,
                verbose = verbose)
        })
    RG$Gb = NULL
    if (offset) {
        RG$G = RG$G + offset
    }
    new("RGList", unclass(RG))
}
