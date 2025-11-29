#include <iostream>
#include "fibffm.h"

int fibonacci(int n) {
    std::cout << "Start ffm fibonacci" << std::endl;
    int prev = 0, result = 1;
    for (int i = 1; i < n; i++) {
        int old_result = result;
        result = result + prev;
        prev = old_result;
    }
    std::cout << "Finish ffm fibonacci" << std::endl;
    return result;
}
