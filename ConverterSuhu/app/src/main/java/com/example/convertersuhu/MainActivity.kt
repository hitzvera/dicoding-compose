package com.example.convertersuhu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.convertersuhu.ui.theme.ConverterSuhuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConverterSuhuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ConverterApp()
                }
            }
        }
    }
}

@Composable
fun ConverterApp() {
    var celcius by remember { mutableStateOf("") }
    var fahrenheit by remember { mutableStateOf("0") }
    var kelvin by remember { mutableStateOf("0") }

    Column {
        StatelessTemperatureConverter(
            inputValue = celcius,
            convertedValue = fahrenheit,
            onValueChanged = { newValue ->
                celcius = newValue.trim()
                fahrenheit = celsuiusToFahrenheit(celcius)
            }
        )
        StatefulTemperatureInput()
        TwoWayConverter()
    }
    
}
@Composable
fun StatefulTemperatureInput() {
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.stateful_converter),
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        OutlinedTextField(
            value = input ,
            label = { Text(stringResource(id = R.string.enter_celsius)) },
            onValueChange = { newValue ->
                input = newValue.trim()
                output = celsuiusToFahrenheit(newValue.trim())
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = stringResource(id = R.string.temperature_fahrenheit, output)
        )
    }
}

@Composable
fun TwoWayConverter(
    modifier: Modifier = Modifier
) {
    var celcius by remember { mutableStateOf("") }
    var fahrenheit by remember { mutableStateOf("") }
    Column(modifier) {
        Text(
            text = stringResource(id = R.string.two_way_converter),
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        OutlinedTextField(value = celcius, onValueChange = {
            celcius = it
            fahrenheit = celsuiusToFahrenheit(it)
        })
        OutlinedTextField(value = fahrenheit, onValueChange = {
            fahrenheit = it
            celcius = fahrenheitToCelcius(it)
        })
    }
}
@Composable
fun StatelessTemperatureConverter(
    inputValue: String,
    convertedValue: String,
    onValueChanged: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.stateless_converter),
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        OutlinedTextField(
            value = inputValue,
            label = { Text(stringResource(id = R.string.enter_celsius)) },
            onValueChange = { onValueChanged(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = stringResource(id = R.string.temperature_fahrenheit, convertedValue)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StatefulTemperatureInputPreview() {
    ConverterSuhuTheme {
        ConverterApp()
    }
}

private fun celsuiusToFahrenheit(celsius: String) =
    celsius.toDoubleOrNull()?.let {
        it * 1.8 + 32
    }.toString()

private fun fahrenheitToCelcius(fahrenheit: String) =
    fahrenheit.toDoubleOrNull()?.let {
        (it - 32) * 5 / 9
    }.toString()
