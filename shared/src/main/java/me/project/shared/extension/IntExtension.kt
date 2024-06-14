package me.project.shared.extension

fun Int.idToImageUrl(): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$this.png"
}

fun Int.fibonacciRename(): Long {
    if (this<=0) return 0
    if (this<=1) return 1

    var a = 1
    var b = 1
    var fib = 1

    for (i in 3..this) {
        fib = a + b
        a = b
        b = fib
    }

    return  fib.toLong()
}

fun Long?.counter(): Long {
    return if (this == null) {
        0
    } else {
        this + 1
    }
}

fun Int.isPrimeNumber(): Boolean {
    return this % 2 != 0
}

fun Int.heightWeightCalculate() : Double {
    return (this.toDouble()/10)
}