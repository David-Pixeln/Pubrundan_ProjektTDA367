import { Icon } from "@components/icon";
import * as VectorIcons from '@expo/vector-icons';
import { SearchBar} from "./SearchBar";
import { SearchBarStyles } from "./SearchBar.style";
import { View } from "react-native";
import { ThemeContext } from "@constants/themes";
import { useContext } from "react";


export function SearchBarWithFilter() {
  const theme = useContext(ThemeContext);
  const styles = SearchBarStyles(theme);

  return (
    <View style={styles.viewContainer}>
      <SearchBar />
      <Icon iconType={VectorIcons.FontAwesome} name='filter' />
    </View>
  );
}