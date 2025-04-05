package org.hyperskill.ordersmenu

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.hyperskill.ordersmenu.theme.PlayOrdersMenuTheme

private val ordersList = mutableMapOf<String, MutableState<Int>>()

private var recipesNameToStockAmount = mapOf(
    "Fettuccine" to 5,
    "Risotto" to 6,
    "Gnocchi" to 4,
    "Spaghetti" to 3,
    "Lasagna" to 5,
    "Steak Parmigiana" to 2
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val recipeStock = remember { mutableStateOf(recipesNameToStockAmount) }

            PlayOrdersMenuTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        OrdersMenu()
                        recipesNameToStockAmount.forEach { (name, _) ->
                            DisplayOrdersMenuScreen(name, recipeStock)
                        }
                        MadeOrderButton(recipeStock)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun OrdersMenu() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Orders Menu",
            fontSize = 48.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun DisplayOrdersMenuScreen(name: String, recipesStock: MutableState<Map<String, Int>>) {
    val stockAmount = recipesStock.value[name] ?: 0
    val amountOrdered = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        Row {
            Text(
                text = name,
                fontSize = 24.sp,
                color = if (stockAmount == amountOrdered.value) Color.Red else Color.Black,
                modifier = Modifier.padding(horizontal = 10.dp)
            )

            Text(
                text = "+",
                fontSize = 24.sp,
                modifier = Modifier
                    .clickable {
                        if (amountOrdered.value < stockAmount) {
                            amountOrdered.value++
                            ordersList[name] = amountOrdered
                        }
                    }
                    .padding(horizontal = 10.dp)
            )

            Text(
                text = amountOrdered.value.toString(),
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )

            Text(
                text = "-",
                fontSize = 24.sp,
                modifier = Modifier.clickable {
                    if (amountOrdered.value > 0) {
                        amountOrdered.value--
                        if (amountOrdered.value == 0) {
                            ordersList.remove(name)
                        }
                    }
                }
            )
        }
    }
}


@Composable
fun MadeOrderButton(recipesStock: MutableState<Map<String, Int>>) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
            onClick = {
                val orderMsg = StringBuilder()

                val updatedStock = recipesStock.value.toMutableMap() // Створємо копію запасів продуктів

                orderMsg.append("Ordered:")
                ordersList.forEach { (name, amountOrdered) ->
                    orderMsg.append("\n==> ")
                    orderMsg.append(name)
                    orderMsg.append(": ")
                    orderMsg.append("${amountOrdered.value}")

                    // Додаємо залишки страв
                    val remainingStock = updatedStock[name] ?: 0
                    val newStock = (remainingStock - amountOrdered.value).coerceAtLeast(0)
                    updatedStock[name] = newStock
                }

                if (ordersList.isNotEmpty()) {
                    Toast.makeText(context, orderMsg, Toast.LENGTH_SHORT).show()
                }

                // Відновлюємо кілкість запасів продуктів
                recipesStock.value = updatedStock

                // Скидаємо запаси проудктів
                ordersList.forEach { (_, amount) -> amount.value = 0 }
                ordersList.clear()
            }
        ) {
            Text(text = "Make Order", color = Color.White, fontSize = 24.sp)
        }
    }
}
