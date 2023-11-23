import { CustomTheme } from '@constants/themes';
import { StyleSheet } from 'react-native';



export const ProfilePageStyles = (theme: CustomTheme) => StyleSheet.create({ 
  pageContainer: {
    flex: 1,
    backgroundColor: theme.colors.background.s200,
  },
  profileSummaryContainer: {
    width: '100%',

    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    gap: 10,
  },
  profilePictureContainer: {
    flex: 1,
  },
  profilePicture: {
    width: '100%',
    aspectRatio: 1,

    borderRadius: 100,
    borderWidth: 1,
  },
  addToStoryBadge: {

  },
  userInfoContainer: {
    gap: 10,
  },
  userStatsContainer: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    gap: 10,
  },
  map: {
    width: '100%',
    height: '100%',
  }
});